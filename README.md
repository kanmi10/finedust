# 😷  “오늘미세어때?” 미세먼지 정보 웹사이트

> SpringBoot 환경 대기오염 API를 활용한 **미세먼지 정보 제공 웹사이트**

> <i>개인 프로젝트</i>

<br>

## 개요
- **프로젝트 이름**: 오늘미세어때?
- **프로젝트 기간**: 2024. 10 ~
- **핵심 기능**: 전국 대기오염 농도 조회, 측정소명 자동 완성, 측정소 북마크, 지역 게시판, 이메일 인증 회원가입

<br>

## Skill
<span>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
</span>

<br>

## ERD
![전체 ERD 2024 12 23](https://github.com/user-attachments/assets/2918fdc3-271e-4eb6-b9e4-ad06b6db2559)

<br>


## 화면 구성

|메인 화면|지역 게시판|
|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/750fb26e-efb9-483b-ae55-cd6c016ef47c" width="500"/>|<img src="https://github.com/user-attachments/assets/59425431-c846-4eef-aa36-32798231dbb0" width="500"/>|

|회원 가입|게시글 상세|
|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/3bab7bb7-0cae-4c01-b415-be48eed58992" width="500"/>|<img src="https://github.com/user-attachments/assets/323ea2cf-5dda-4af7-9daf-9b2f8c9e1b80" width="500"/>|

## 💡 주요 기능

|측정소명 자동 완성|
|:---:|
|<img src="https://github.com/user-attachments/assets/86802642-3f98-4169-8c1a-fb4f5d2dd9e5" width="500"/>|
|<div align="left">▪︎  검색 가능한 측정소명을 자동 완성</div>|


|북마크|
|:---:|
|<img src="https://github.com/user-attachments/assets/85f74e5b-fbc3-4601-a7b2-9d68480cee59" width="500"/>|
|<div align="left">▪︎  측정소를 북마크해 빠른 조회 가능<br>▪︎  버튼을 통해 북마크 설정, 반복 클릭 시 해제</div>|


|이메일 인증 회원가입|
|:---:|
|<img src="https://github.com/user-attachments/assets/24980913-7196-4e0a-a211-06bd87db522a" width="500"/>|
|<div align="left">▪︎  회원가입 시 이메일 인증 필요<br>▪︎  인증코드 입력 시 이메일 인증 통과</div>|

<br>

## 진행하면서 마주친 어려움 & 체득한 부분
- **Development**
    - [AJAX fetch를 활용한 검색어 자동 완성 기능 구현](https://velog.io/@kanmi10/%EA%B2%80%EC%83%89%EC%96%B4-%EC%9E%90%EB%8F%99-%EC%99%84%EC%84%B1-%EA%B8%B0%EB%8A%A5)
    - [AJAX와 자료구조의 특징을 고려한 북마크 기능 구현](https://velog.io/@kanmi10/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EB%B6%81%EB%A7%88%ED%81%AC-%EA%B8%B0%EB%8A%A5-%EC%B6%94%EA%B0%80)
    - [Bean Validation을 이용한 회원가입 폼 검증](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-Bean-Validation)
    - [게시판 페이징 구현기](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B2%8C%EC%8B%9C%ED%8C%90-%ED%8E%98%EC%9D%B4%EC%A7%95) 
    - [Spring Boot 이메일 인증을 활용한 회원가입 시스템 구현](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85-1) 
- **Design & Data Management**
    - [대기오염 데이터 DB 저장 구조 설계](https://velog.io/@kanmi10/%EB%8C%80%EA%B8%B0%EC%98%A4%EC%97%BC-API-DB-%EC%A0%80%EC%9E%A5-%EB%B0%A9%EC%8B%9D-%EA%B3%A0%EB%AF%BC) 
    - [타임리프 템플릿 조각을 활용한 HTML 관리](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%99%88-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%ED%99%88-HTML-%EB%B6%84%EB%A6%AC)
    - [회원 탈퇴 기능 Soft / Hard Delete 고민](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%9A%8C%EC%9B%90%ED%83%88%ED%87%B4-%EA%B8%B0%EB%8A%A5)
- **Refactoring**
    - [필터, 인터셉터를 활용한 공통 관심사 분리](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%B2%98%EB%A6%AC-%ED%95%84%ED%84%B0-%EC%9D%B8%ED%84%B0%EC%85%89%ED%84%B0)
    - [리포지토리 순수 JDBC → MyBatis 리팩토링](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%88%9C%EC%88%98-JDBC-MyBatis-%EC%A0%81%EC%9A%A9-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81) 
    - [MyBatis 배치 처리로 대기오염 데이터 업데이트 성능 최적화](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-MyBatis-%EB%B0%B0%EC%B9%98%EC%9E%91%EC%97%85) 
- **Test & Problem**
    - [MyBatis <foreach> 다중 쿼리 제한 문제 해결](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-MyBatis-foreach-%EB%8B%A4%EC%A4%91-%EC%BF%BC%EB%A6%AC-update)
    - [H2 임베디드 모드를 활용한 테스트 코드 작성](https://velog.io/@kanmi10/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-H2-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%9E%84%EB%B2%A0%EB%94%94%EB%93%9C-%EB%AA%A8%EB%93%9C%EB%A1%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1)

