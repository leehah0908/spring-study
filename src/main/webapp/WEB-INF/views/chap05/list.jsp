<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
        <%@ include file="../include/static-head.jsp" %>
        <link rel="stylesheet" href="/assets/css/list.css" />
    </head>
    <body>
        <%@ include file="../include/header.jsp" %>

        <div id="wrap">
            <div class="main-title-wrapper">
                <h1 class="main-title">꾸러기 게시판</h1>
                <button class="add-btn">새 글 쓰기</button>
            </div>

            <div class="top-section">
                <!-- 검색창 영역 -->
                <div class="search">
                    <form action="/board/list" method="get">
                        <select class="form-select" name="type" id="search-type">
                            <option value="title">제목</option>
                            <option value="content">내용</option>
                            <option value="writer">작성자</option>
                            <option value="tc">제목+내용</option>
                        </select>
                        <input type="text" class="form-control" name="keyword" />
                        <button class="btn btn-primary" type="submit">
                            <i class="fas fa-search"></i>
                        </button>
                    </form>
                </div>
                <div class="amount">
                    <div><a href="/board/list?pageNo=1&amount=6">6</a></div>
                    <div><a href="/board/list?pageNo=1&amount=18">18</a></div>
                    <div><a href="/board/list?pageNo=1&amount=30">30</a></div>
                </div>
            </div>

            <!-- 메인 게시판 영역 -->
            <div class="card-container">
                <c:forEach var="b" items="${bList}">
                    <div class="card-wrapper">
                        <section class="card" data-bno="${b.boardNo}">
                            <div class="card-title-wrapper">
                                <h2 class="card-title">${b.shortTitle}</h2>
                                <div class="time-view-wrapper">
                                    <div class="time">
                                        <i class="far fa-clock"></i>
                                        ${b.regDate}
                                    </div>
                                    <div class="view">
                                        <i class="fas fa-eye"></i>
                                        <span class="view-count">${b.viewCount}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="card-content">${b.shortContent}</div>
                        </section>
                        <div class="card-btn-group">
                            <button class="del-btn" data-href="${b.boardNo}">
                                <i class="fas fa-times"></i>
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- 게시글 목록 하단 영역 -->
            <div class="bottom-section">
                <!-- 페이지 버튼 영역 -->
                <nav aria-label="Page navigation example">
                    <ul class="pagination pagination-lg pagination-custom">
                        <li class="page-item">
                            <a
                                class="page-link"
                                href="/board/list?pageNo=1&amount=${maker.page.amount}"
                                >&lt;&lt;</a
                            >
                        </li>

                        <c:if test="${maker.prev}">
                            <li class="page-item">
                                <a
                                    class="page-link"
                                    href="/board/list?pageNo=${maker.begin - 1}&amount=${maker.page.amount}"
                                    >prev</a
                                >
                            </li>
                        </c:if>

                        <!-- fori 반목문이고, step은 기본값이 1이고, 생략이 가능함 -->
                        <c:forEach var="i" begin="${maker.begin}" end="${maker.end}">
                            <li data-page-num="${i}" class="page-item">
                                <a
                                    class="page-link"
                                    href="/board/list?pageNo=${i}&amount=${maker.page.amount}"
                                    >${i}</a
                                >
                            </li>
                        </c:forEach>

                        <c:if test="${maker.next}">
                            <li class="page-item">
                                <a
                                    class="page-link"
                                    href="/board/list?pageNo=${maker.end + 1}&amount=${maker.page.amount}"
                                    >next</a
                                >
                            </li>
                        </c:if>

                        <li class="page-item">
                            <a
                                class="page-link"
                                href="/board/list?pageNo=${maker.finalPage}&amount=${maker.page.amount}"
                                >&gt;&gt;</a
                            >
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

        <form action="/board/delete" method="post" name="removeForm">
            <input type="hidden" id="remove-bno" name="boardNo" />
        </form>

        <!-- 모달 창 -->
        <div class="modal" id="modal">
            <div class="modal-content">
                <p>정말로 삭제할까요?</p>
                <div class="modal-buttons">
                    <button class="confirm" id="confirmDelete">
                        <i class="fas fa-check"></i> 예
                    </button>
                    <button class="cancel" id="cancelDelete">
                        <i class="fas fa-times"></i> 아니오
                    </button>
                </div>
            </div>
        </div>

        <script>
            //========== 게시물 목록 스크립트 ============//
            // 카드 형태의 게시물들을 감싸고 있는 부모 요소 취득
            const $cardContainer = document.querySelector(".card-container");

            // 삭제에 필요한 요소들을 먼저 얻겠습니다.
            const $modal = document.getElementById("modal"); // 모달창 얻기
            const $confirmDelete = document.getElementById("confirmDelete"); // 모달 삭제 확인버튼
            const $cancelDelete = document.getElementById("cancelDelete"); // 모달 삭제 취소버튼

            $cardContainer.addEventListener("click", (e) => {
                // if (!e.target.matches("section.card")) return;
                if (e.target.matches(".card-container")) return;

                // 삭제버튼을 눌렀다면
                if (e.target.matches(".card-btn-group *")) {
                    $modal.style.display = "flex"; // 모달창 보여주기

                    // 이벤트가 발생된 타겟에서 가장 가까운 .del-btn이 갖고 있는 글번호 얻기
                    const delTargetBno = e.target.closest(".del-btn").dataset.href;

                    // 삭제 확인 버튼 이벤트
                    $confirmDelete.onclick = () => {
                        // js로 form을 생성할 수 있음 -> post로 요청보낼 것
                        // 1. form 객체를 js로 생성해서 submit -> 비동기 전용
                        // 비동기 전용 요청
                        // const formData = new FormData();

                        // 2. html form 태그를 작성해서 submit
                        document.getElementById("remove-bno").value = delTargetBno;
                        document.removeForm.submit();
                    };

                    // 삭제 취소 버튼 이벤트
                    $cancelDelete.onclick = () => {
                        $modal.style.display = "none";
                    };
                }

                // section 태그에 붙은 글번호 읽기
                // 이벤트가 발생된 타겟이 계속해서 달라지고 있음 -> 고정된 상대경로로는 번호를 얻기 힘듦
                // closest(특정 요소 선택자): e.target에서 가장 가까운 특정 요소 선택자를 가져옴
                const bno = e.target.closest("section.card").dataset.bno;
                console.log("asd");
                console.log("글번호" + bno);

                // 서버에 요청 보내기 (파라미터가 아닌 경로로 보낼 수도 았음)
                location.href =
                    "/board/detail/" +
                    bno +
                    "?pageNo=${maker.page.pageNo}&amount=${maker.page.amount}";
            });

            function removeDown(e) {
                if (!e.target.matches(".card-container *")) return;
                const $targetCard = e.target.closest(".card-wrapper");
                $targetCard?.removeAttribute("id", "card-down");
            }

            function removeHover(e) {
                if (!e.target.matches(".card-container *")) return;
                const $targetCard = e.target.closest(".card");
                $targetCard?.classList.remove("card-hover");
                const $delBtn = e.target.closest(".card-wrapper")?.querySelector(".del-btn");
                $delBtn.style.opacity = "0";
            }

            $cardContainer.onmouseover = (e) => {
                if (!e.target.matches(".card-container *")) return;
                const $targetCard = e.target.closest(".card");
                $targetCard?.classList.add("card-hover");
                const $delBtn = e.target.closest(".card-wrapper")?.querySelector(".del-btn");
                $delBtn.style.opacity = "1";
            };

            $cardContainer.onmousedown = (e) => {
                if (e.target.matches(".card-container .card-btn-group *")) return;
                const $targetCard = e.target.closest(".card-wrapper");
                $targetCard?.setAttribute("id", "card-down");
            };

            $cardContainer.onmouseup = removeDown;
            $cardContainer.addEventListener("mouseout", removeDown);
            $cardContainer.addEventListener("mouseout", removeHover);

            // write button event
            document.querySelector(".add-btn").onclick = (e) => {
                window.location.href = "/board/write";
            };

            // 사용지기 현제 머물고 있는 페이지 버튼에 active 스타일 구현 <- 현재 페이지에 맞는 li에 active 요소 추가하기 -> 동적으로 처리하기 위해
            function appendPageActive() {
                // 현재 서버에서 넘겨준 페이지 번호 얻기
                const currentPage = "${maker.page.pageNo}";

                // ul을 지목하고, ul의 자식들을 배열로 받음
                const $ul = document.querySelector(".pagination");
                const $liList = [...$ul.children]; // 유사배열이기 때문에 배열로 재처리해야 함

                $liList.forEach(($li) => {
                    if (currentPage === $li.dataset.pageNum) {
                        $li.classList.add("active");
                    }
                });
            }

            appendPageActive();
        </script>
    </body>
</html>
