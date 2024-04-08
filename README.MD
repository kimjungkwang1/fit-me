
<div style="text-align:center;">
    <img src="/exec/resources/logo.png" width="50%" />
</div>

<br />
<br />

# 🛒🛍️Fit Me

> AI 피팅/추천 패션 쇼핑몰

**피팅 AI**를 통해 사용자가 제품을 미리 입어보고 **사회적 시간과 비용을 절약**

<br />

#  📘 프로젝트 진행 기간

2024.02 ~ 202.04 (6주)

<br />


# 👕 주요 기능

* 피팅 AI로 미리 입어보는 **드레스룸 기능**
* 메타 데이터를 활용한 **유사 상품 추천**(Content-based Filtering)
* Batch를 활용한 **실시간 랭킹 / 인기도 계산**

<br />

# 📚 목차
1. [✔ 주요 기술](#-주요-기술)
   1. [피팅 AI로 미리 입어보는 드레스룸 기능](#피팅-AI로-미리-입어보는-드레스룸-기능)
   2. [메타 데이터를 활용한 유사 상품 추천(Content-based Filtering)](#메타-데이터를-활용한-유사-상품-추천content-based-filtering)
   3. [Batch를 활용한 실시간 랭킹 / 인기도 계산](#batch를-활용한-실시간-랭킹--인기도-계산)
2. [📱 페이지 목록](#-페이지-목록)
3. [🖥️ 인프라](#-인프라)
   1. [시스템 아키텍처](#시스템-아키텍처)
   2. [블루-그린방식을 사용한 무중단 배포](#블루-그린방식을-사용한-무중단-배포)
4. [🛠️ 기술 스택](#-기술-스택)
5. [👪 팀원](#-팀원)

# ✔ 주요 기술

## 피팅 AI로 미리 입어보는 드레스룸 기능

<p align="center">
  <img src="/exec/resources/ai-fitting.png">
</p>

* openpose를 통한 모델 전처리
* COTTON-size-does-matter를 사용한 옷 전처리 및 합성
* 8300여개의 데이터를 통한 하의 학습 weights 생성 및 사용

## 메타 데이터를 활용한 유사 상품 추천(Content-based Filtering)

<p align="center">
    <img src="/exec/resources/코사인유사도.png">
</p>

* 상품의 태그를 활용한 코사인 유사도 계산
<p align="center">
    <img src="/exec/resources/상품추천.png">
</p>

* 함께 보면 좋은 상품 추천

## Batch를 활용한 실시간 랭킹 / 인기도 계산
<p align="center">
    <img src="/exec/resources/실시간랭킹.png">
</p>

<br />

# 📱 페이지 목록

## 메인, 로그인 페이지

<img src="/exec/resources/메인페이지.png" width="30%" />
<img src="/exec/resources/로그인페이지.png" width="30%" />

## 상품상세페이지

<img src="/exec/resources/상품상세페이지.png" width="30%" />
<img src="/exec/resources/리뷰_함께보면좋은상품페이지.png" width="30%" />
<img src="/exec/resources/옵션페이지.png" width="30%" />

## 카테고리, 검색 페이지

<img src="/exec/resources/카테고리페이지.png" width="30%" />
<img src="/exec/resources/검색페이지.png" width="30%" />
<img src="/exec/resources/검색필터_페이지.png" width="30%" />

## 장바구니, 드레스룸 페이지

<img src="/exec/resources/장바구니페이지.png" width="30%" />
<img src="/exec/resources/피팅해보기페이지.gif" width="30%" />
<img src="/exec/resources/내피팅목록페이지.png" width="30%" />

## 마이페이지

<img src="/exec/resources/구매목록페이지.png" width="30%" />
<img src="/exec/resources/좋아요페이지.png" width="30%" />
<img src="/exec/resources/회원정보수정페이지.png" width="30%" />

## 피드페이지

<img src="/exec/resources/피드페이지.png" width="30%" />

<br />

# 🖥️ 인프라

## 시스템 아키텍처
<p align="center">
  <img src="/exec/resources/infra.png">
</p>

- 자체 이미지 서버 직접 구축
- GPU서버에 장고로 만든 AI서버 가동

## 블루-그린방식을 사용한 무중단 배포
<p align="center">
  <img src="/exec/resources/blue-green.png">
</p>

<br />

# 🛠️ 기술 스택
### BackEnd
<p align="center">
<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/jpa-000000?style=for-the-badge&logo=jpa&logoColor=white">
<img src="https://img.shields.io/badge/QueryDSL-000000?style=for-the-badge&logo=QueryDSL&logoColor=white">
<img src="https://img.shields.io/badge/django-092E20?style=for-the-badge&logo=django&logoColor=white">
<img src="https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/springbatch-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">


### FrontEnd
<p align="center">
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black">
<img src="https://img.shields.io/badge/node.js-339933?style=for-the-badge&logo=Node.js&logoColor=white">
<img src="https://img.shields.io/badge/typescript-3178C6?style=for-the-badge&logo=typescript&logoColor=white">


### Infra
<p align="center">
<img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">


### 협업 툴
<p align="center">
<img src="https://img.shields.io/badge/gitlab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white">
<img src="https://img.shields.io/badge/jirasoftware-0052CC?style=for-the-badge&logo=jirasoftware&logoColor=white">
<img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white">
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">


<br />

# 👪 팀원
<table>
  <tr>
    <td align="center" width="150px">
      <a href="https://github.com/chacha3088" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/90785316?v=4" alt="차명훈" />
      </a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com/yuyeoul" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/93958447?v=4" alt="배유열" />
      </a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com/s-yeong" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/102036033?v=4" alt="이상영" />
      </a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com//kimjungkwang1" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/134460604?v=4" alt="김중광" />
      </a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com/seonghyeon-m" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/139305010?v=4" alt="문성현" />
      </a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com/SiyeonYoo" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/75736981?v=4" alt="유시연" />
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/Jo-wonbin" target="_blank">
        차명훈<br />팀장 (Back-end)
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/dudqo225" target="_blank">
        배유열<br />(Back-end)
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/person003333" target="_blank">
        이상영<br />(Back-end)
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/JeongHwan-dev" target="_blank">
        김중광<br />(Front-end)
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/sojjeong" target="_blank">
        문성현<br />(Front-end)
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/limejin">
        유시연<br />(Front-end)
      </a>
    </td>
  </tr>
</table>
