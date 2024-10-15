package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.request.SignUpRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.response.KakaoUserResponseDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginService {

    private final MemberService memberService;

    public void login(Map<String, String> params, HttpSession session) {

        // 토큰 발급 요청
        String AccessToken = getKakaoAccessToken(params);

        // 발급받은 액세스 토큰으로 사용자 정보 가져오기
        KakaoUserResponseDTO kakaoUserInfo = getKakaoUserInfo(AccessToken);

        // 카카오 정보로 서비스 회원가입 시키기
        if (!memberService.checkIdentifier("email", kakaoUserInfo.getAccount().getEmail())) {

            // 기존에 가입한적 없는 회원이면 회원가입
            memberService.join(SignUpRequestDTO.builder()
                    .account(String.valueOf(kakaoUserInfo.getId()))
                    .password(UUID.randomUUID().toString())
                    .name(kakaoUserInfo.getProperties().getNickname())
                    .email(kakaoUserInfo.getAccount().getEmail())
                    .build(), kakaoUserInfo.getProperties().getProfileImage());

            // 로그인 처리
            memberService.maintainLoginStatus(session, String.valueOf(kakaoUserInfo.getId()));
        }
    }

    private String getKakaoAccessToken(Map<String, String> requestParam) {

        // 요청 URI
        String requestUri = "https://kauth.kakao.com/oauth/token";

        // 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 바디에 파라미터 세팅
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestParam.get("appKey"));
        params.add("redirect_uri", requestParam.get("redirect"));
        params.add("code", requestParam.get("code"));

        // 카카오 인증 서버로 Post 요청 날리기
        RestTemplate template = new RestTemplate();

        // 헤더 정보와 파라미터를 하나로 묶기
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        /*
        - RestTemplate객체가 REST API 통신을 위한 API인데 (자바스크립트 fetch역할)
                - 서버에 통신을 보내면서 응답을 받을 수 있는 메서드가 exchange
        param1: 요청 URL
        param2: 요청 방식 (get, post, put, patch, delete...)
        param3: 요청 헤더와 요청 바디 정보 - HttpEntity로 포장해서 줘야 함
        param4: 응답결과(JSON)를 어떤 타입으로 받아낼 것인지 (ex: DTO로 받을건지 Map으로 받을건지)
         */

        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);

        // 응답 데이터에서 json 추출 (카카오 로그인 중인 사용자의 정보를 요청할 때 사용해야 함)
        Map<String, Object> responseJSON = responseEntity.getBody();
        log.info("응답 JSON 데이터: {}", responseJSON);

        return (String) responseJSON.get("access_token");
    }

    private KakaoUserResponseDTO getKakaoUserInfo(String accessToken) {

        String requestURI = "https://kapi.kakao.com/v2/user/me";

        // 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 보내기
        RestTemplate template = new RestTemplate();
        ResponseEntity<KakaoUserResponseDTO> responseEntity =
                template.exchange(requestURI, HttpMethod.POST, new HttpEntity<>(headers), KakaoUserResponseDTO.class);

        KakaoUserResponseDTO userInfo = responseEntity.getBody();

        return userInfo;

    }
}
