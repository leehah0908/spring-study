// ==================== 회원가입 입력 검증 처리 ====================
const url = "/members/check";
let keywordFlag = false;

// 계정 중복 검사 비동기 요청 보내기
async function fetchDuplicateCheck(type, keyword) {
    // fetch(`\${url}?type=\${type}&keyword=\${keyword}`)
    //     .then((res) => res.json())
    //     .then((flag) => (keywordFlag = flag));

    const res = await fetch(`${url}?type=${type}&keyword=${keyword}`);
    const flag = await res.json();
    keywordFlag = flag;
}

// ==================== 회원가입 입력 검증 처리 ====================
// 아이디 검사
// 계정 입력 검증
const $idInput = document.getElementById("user_id");

// async: 이 이벤트는 비동기라는 것을 알려줌
$idInput.addEventListener("keyup", async (e) => {
    const idValue = $idInput.value;
    const accountPattern = /^[a-zA-Z0-9]{4,14}$/;
    const $idCheck = document.getElementById("idChk");

    if (idValue.trim() === "") {
        $idInput.style.borderColor = "red";
        $idCheck.innerHTML = '<b class="warning">[아이디는 필수값입니다.]</b>';
    } else if (!accountPattern.test(idValue)) {
        $idInput.style.borderColor = "red";
        $idCheck.innerHTML = '<b class="warning">[아이디는 4 ~ 14자, 영문, 숫자로만 구성]</b>';
    } else {
        // 아이디 중복 검사
        await fetchDuplicateCheck("account", idValue); // 이 함수를 실행할 때 끝날때 까지 기다려

        if (keywordFlag) {
            $idInput.style.borderColor = "red";
            $idCheck.innerHTML = '<b class="warning">[이미 존재하는 아이디입니다.]</b>';
        } else {
            $idInput.style.borderColor = "green";
            $idCheck.innerHTML = '<b class="success">[사용할 수 있는 아이디입니다.]</b>';
        }
    }
});

// 패스워드 검사
const passwordPattern =
    /([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/;

// 이름 검사
const namePattern = /^[가-힣]+$/;

// 이메일 검사
const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
