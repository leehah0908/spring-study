package com.study.springstudy.springmvc.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class FileUtils {
    /**
     * 사용자가 클라이언트에서 파일을 전송했을 때
     * 중복이 없는 새로운 파일명을 생성하여 해당 파일명으로
     * 날짜별 폴더로 업로드하는 메서드
     *
     * @param profileImage - 사용자가 업로드한 파일의 정보객체
     * @param rootPath - 서버에 업로드할 루트 디렉토리 경로
     *                   ex) D:/spring-prj/upload
     * @return - 업로드가 완료되었을 경우 업로드 된 파일의 위치 경로
     *                   ex)  /2023/12/29/djfalsjdflaksjlfdsaj_고양이.jpg
     **/

    public static String uploadFile(MultipartFile profileImage, String rootPath) {

        String uuid = UUID.randomUUID()
                .toString()
                .replace("-","");

        String fileType = profileImage.getOriginalFilename()
                .substring(profileImage.getOriginalFilename().lastIndexOf("."));

        // 날짜별로 관리하기 위해 날짜별 폴더 생성
        String fullPath = makeDateFormatDirectory(rootPath);

        // return에서는 풀 경로를 사용하지 않지만 업로드 할떄는 풀 경로가 필요하기 때문에 아래 로직이 필요함
        File uploadPath = new File(fullPath, uuid + fileType);

        try {
            profileImage.transferTo(uploadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadPath.getPath().substring(rootPath.length());
    }

    /**
     * 루트 경로를 받아서 일자별로 폴더를 생성한 후
     * 루트 경로 + 날짜폴더 경로를 반환
     *
     * @param rootPath - 파일 업로드 루트 경로  (ex) D:/spring-prj/upload
     * @return - 날짜 폴더 경로가 포함된 새로운 업로드 경로
     * (ex)  D:/spring-prj/upload/2023/12/29
     */
    private static String makeDateFormatDirectory(String rootPath) {
        // 오늘 날짜 정보 출력
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        String[] dateInfo = {year + "", len2(month), len2(day)};
        String directoryPath = rootPath;

        for (String s : dateInfo) {
            directoryPath += "/" + s;
            File f = new File(directoryPath);
            if (!f.exists()) f.mkdir();
        }
        return directoryPath;
    }

    /**
     * 한글자 월과 한글자 일자를 두글자로 변환해주는 메서드
     * ex)  2023-6-7   => 2023-06-07
     * @param n - 원본 일자나 월자
     * @return - 앞에 0이붙은 일자나 월자
     */
    private static String len2(int n) {
        return new DecimalFormat("00").format(n);
    }
}
