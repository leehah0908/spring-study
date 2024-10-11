// 디스트럭처링을 통해 특정 js파일에서 원하는 함수만 가져오기
import { validateInput } from "./validation.js";
import { debounce } from "./util.js";

// 폼과 회원가입 버튼 요소를 가져옴
const form = document.getElementById("signUpForm");
const signupButton = document.getElementById("signup-btn");

// 버튼 상태를 업데이트하는 함수
const updateButtonState = () => {
    // 모든 valid가 true인지 확인
    // every: 배열 내의 데이터를 순회하면서 특정 값이 모두 true인지 확인
    const isFormValid = fields.every((field) => field.valid);
    if (isFormValid) {
        signupButton.disabled = false;
        signupButton.style.backgroundColor = "orangered"; // 활성화된 버튼 배경색
    } else {
        signupButton.disabled = true;
        signupButton.style.backgroundColor = "lightgray"; // 비활성화된 버튼 배경색
    }
};

// 각 필드에 대한 정보 배열 (id, 유효성 검증 함수, 에러 메시지 표시 요소, 초기 유효 상태)
const fields = [
    {
        id: "user_id",
        errorElement: "idChk",
        validator: validateInput.account,
        valid: false,
    },
    {
        id: "password",
        errorElement: "pwChk",
        validator: validateInput.password,
        valid: false,
    },
    {
        id: "password_check",
        errorElement: "pwChk2",
        validator: (value) =>
            validateInput.passwordCheck(value, document.getElementById("password").value),
        valid: false,
    },
    {
        id: "user_name",
        errorElement: "nameChk",
        validator: validateInput.name,
        valid: false,
    },
    {
        id: "user_email",
        errorElement: "emailChk",
        validator: validateInput.email,
        valid: false,
    },
];

// 각 필드에 대해 입력값 검증 이벤트 리스너를 추가
fields.forEach((field) => {
    const $input = document.getElementById(field.id); // 입력 요소 가져오기
    $input.addEventListener(
        "keyup",
        debounce(async (e) => {
            // 키보드 입력 시마다 유효성 검증
            const isValid = await field.validator($input.value); // 유효성 검증 함수 호출 -> 값을 검증하는 부분
            const $errorSpan = document.getElementById(field.errorElement); // 에러 메시지 표시 요소 가져오기

            if (isValid.valid) {
                // 유효한 경우
                $input.style.borderColor = "green"; // 입력 요소의 테두리 색 변경
                $errorSpan.innerHTML = '<b class="success">[사용가능합니다.]</b>'; // 성공 메시지 표시
                field.valid = true; // 필드의 유효 상태를 true로 설정
            } else {
                // 유효하지 않은 경우
                $input.style.borderColor = "red"; // 입력 요소의 테두리 색 변경
                $errorSpan.innerHTML = `<b class="warning">[${isValid.message}]</b>`; // 에러 메시지 표시
                field.valid = false; // 필드의 유효 상태를 false로 설정
            }
            updateButtonState(); // 각 입력 유효성 검사 후 버튼 상태 업데이트
        }, 500)
    );
});

// 회원가입 버튼 클릭 이벤트 리스너 추가
signupButton.addEventListener("click", (e) => {
    // 모든 필드가 유효한지 확인 -> 더블 체크
    const isFormValid = fields.every((result) => result.valid);
    if (isFormValid) {
        // 모든 필드가 유효한 경우
        form.submit(); // 폼 제출
    } else {
        // 유효하지 않은 필드가 있는 경우
        alert("입력란을 다시 확인하세요!"); // 경고 메시지 표시
    }
});
