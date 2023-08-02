## 기존 투표 프로젝트에 Controller 적용한 버전 
1. jsp는 기존 프로젝트에서 직접 DAO에서 데이터를 가져오는 방식 대신 controller에서 넘긴 request에 담긴 데이터를 꺼내는 방식으로 수정됨. 
2. Controller 클래스를 추가하여 주소요청을 받고 DAO를 호출하는 방식으로 구성됨. 

## 프로젝트 기능
1. 후보조회
2. 투표하기    
3. 투표검수조회
   1. 투표 결과를 보여주는 기능으로 쿼리를 매우 신경써야함.
   2. join은 하지 않지만 DB에 저장된 데이터를 다른 형식으로 보여줘야 함. 
4. 후보자 등수 조회
    1. join, group by 해야함. 

## 프로젝트 세팅 순서
1. dynamic web project 생성
    - 이클립스 File-New-Other 클릭 후 검색창에 dynamic web project 검색 또는 web 폴더 안에 있음.
    - 프로젝트 이름만 설정하면 다른 설정은 크게 필요하지 않음.
2. 프로젝트 세팅하고 jsp 파일은 src-main-webapp 폴더 안에 작성
3. java 클래스는 src-main-java 패키지안에 필요한 패키지 만들고 필요한 클래스 만듦.    
    - 조회 작업 등 할때는 DTO 클래스 만드는 것 권장.
4. DB 작업을 위해선 ojdbc11.jar 파일이 필요함. (평가시에는 미리 세팅되어 있거나 PC 바탕화면 등에 제공될 가능성 높음.)
    - 파일을 src-main-webapp-WEB-INF-lib 폴더 안에 가져다놓음.
    - ojdbc11.jar 라이브러리가 있어야 DAO 클래스에서 Connection, PreparedStatement, ResultSet 등의 객체 사용 가능.
5. Servlet이 제대로 적용되지 않을 때 
	- 프로젝트 이름에서 우클릭후 제일 아래 properties 클릭
	- 좌측 메뉴에서 Project Facet 클릭 
	- 우측에 보면 Runtimes라는 탭을 클릭하여 Tomcat을 체크함. 

## 사용 쿼리
1. 특히 insert 쿼리를 DB에 직접 수행하게 되면 commit을 반드시 해야함. 
2. 본 예제 쿼리는 길고 복잡함.(단계별로 표현해야 하는 내용을 작성하였으니 주석을 잘 참고할 것)
```
-- 투표이력 테이블 생성 
drop table tbl_vote_202005;
create table tbl_vote_202005(
    v_jumin char(13) not null primary key,
    v_name varchar2(20),
    m_no char(1), 
    v_time char(4),
    v_area char(20),
    v_confirm char(1)
    );
select * from tbl_vote_202005;    
-- 투표이력 데이터 저장
insert into tbl_vote_202005 values ('99010110001', '김유권', '1', '0930', '제1투표장', 'N');
insert into tbl_vote_202005 values ('89010120002', '이유권', '2', '0930', '제1투표장', 'N');
insert into tbl_vote_202005 values ('69010110003', '박유권', '3', '0930', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('59010120004', '홍유권', '4', '0930', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('79010110005', '조유권', '5', '0930', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('89010120006', '최유권', '1', '0930', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('59010110007', '지유권', '1', '0930', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('49010120008', '장유권', '3', '0930', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('79010110009', '정유권', '3', '0930', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('89010120010', '강유권', '4', '0930', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('99010110011', '신유권', '5', '0930', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('79010120012', '오유권', '1', '1330', '제1투표장', 'Y');
insert into tbl_vote_202005 values ('69010110013', '현유권', '4', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('89010110014', '왕유권', '2', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('99010110015', '유유권', '3', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('79010110016', '한유권', '2', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('89010110017', '문유권', '4', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('99010110018', '양유권', '2', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('99010110019', '구유권', '4', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('79010110020', '황유권', '5', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('69010110021', '배유권', '3', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('79010110022', '전유권', '3', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('99010110023', '고유권', '1', '1330', '제2투표장', 'Y');
insert into tbl_vote_202005 values ('59010110024', '권유권', '3', '1330', '제2투표장', 'Y');

-- 후보자 테이블 생성
create table tbl_member_202005(
    m_no char(1) not null primary key,
    m_name varchar2(20),
    p_code char(2),
    p_school char(1),
    m_jumin char(13),
    m_city varchar2(20)
    );
select * from tbl_member_202005;    
--후보자 데이터 저장
insert into tbl_member_202005 values ('1', '김후보', 'P1', '1', '6603011999991', '수선화동');
insert into tbl_member_202005 values ('2', '이후보', 'P2', '3', '5503011999992', '민들래동');
insert into tbl_member_202005 values ('3', '박후보', 'P3', '2', '7703011999993', '나팔꽃동');
insert into tbl_member_202005 values ('4', '조후보', 'P4', '2', '8803011999994', '진달래동');
insert into tbl_member_202005 values ('5', '최후보', 'P5', '3', '9903011999995', '개나리동');

-- 정당 테이블 생성
drop table tbl_party_202005;
create table tbl_party_202005(
    p_code char(2) not null primary key,
    p_name varchar2(20),
    p_indate date,
    p_reader varchar2(20),
    p_tel1 char(3),
    p_tel2 char(4),
    p_tel3 char(4)
    );
select * from tbl_party_202005;
-- 정당 데이터 저장    
insert into tbl_party_202005 values ('P1', 'A정당', '2010-01-01', '위대표', '02', '1111', '0001');
insert into tbl_party_202005 values ('P2', 'B정당', '2010-02-01', '명대표', '02', '1111', '0002');
insert into tbl_party_202005 values ('P3', 'C정당', '2010-03-01', '기대표', '02', '1111', '0003');
insert into tbl_party_202005 values ('P4', 'D정당', '2010-04-01', '옥대표', '02', '1111', '0004');
insert into tbl_party_202005 values ('P5', 'E정당', '2010-05-01', '임대표', '02', '1111', '0005');

select * from tbl_member_202005 m, tbl_party_202005 p where m.p_code=p.p_code;


-- decode 함수 사용(p_school 값에 따라 학력 값을 주기위해), decode 함수는 오라클에서만 사용하는 함수. case when 문법 사용 권장. 
select m.m_no, m.m_name, p.p_name, decode(m.p_school, '1','고졸', '2','학사', '3','석사', '4','박사') p_school, 
    substr(m.m_jumin, 1, 6) -- 문자열 자르기(주민번호 1번째 부터 6개). 주민번호 앞자리 6글자
    || '-' || -- 중간에 대시 추가를 위해 문자열 합치기 기호(||) 사용 
    substr(m.m_jumin, 7, 7) -- 문자열 자르기(주민번호 7번째 부터 7개). 주민번호 뒷자리
    m_jumin, -- 위에서 작업한 주민번호 보여줄 컬럼 이름을 m_jumin으로 (지정 안하면 substr~~ 이 모두 나옴.)
    m_city,
    p.p_tel1 ||'-'|| p.p_tel2 ||'-'|| p.p_tel3 p_tel -- 전화번호도 같이 표현하기 위해 문자열 합치기 활용
    from tbl_member_202005 m, tbl_party_202005 p where m.p_code=p.p_code;
select m.m_no, m.m_name, p.p_name, decode(m.p_school, '1','고졸', '2','학사', '3','석사', '4','박사') p_school, 
    substr(m.m_jumin, 1, 6) || '-' || substr(m.m_jumin, 7, 7) 
    m_jumin, m_city, 
    p.p_tel1 ||'-'|| p.p_tel2 ||'-'|| p.p_tel3 p_tel 
    from tbl_member_202005 m, tbl_party_202005 p where m.p_code=p.p_code;    
-- case when 사용 
select m.m_no, m.m_name, p.p_name, 
        case when m.p_school='1' then '고졸'
             when m.p_school='2' then '학사'
             when m.p_school='3' then '석사'
             when m.p_school='4' then '박사'
             else '없음'
        end as p_school
        from tbl_member_202005 m, tbl_party_202005 p where m.p_code=p.p_code;

select * from tbl_member_202005;


-- 투표검수 조회
-- 주민번호를 생년월일로 표현하기
select v_jumin from tbl_vote_202005; -- 주민번호 조회
select substr(v_jumin, 1, 6) from tbl_vote_202005; -- 생년월일(앞6자리) 조회
-- 생년월일(char 타입)의 6자리만 date타입으로 변환 후 00년 00월 00일 형식으로 조회
select to_char(to_date(substr(v_jumin, 1, 6)), 'YY"년" MM"월" DD"일"') from tbl_vote_202005; 
-- 년도 4자리 표현을 위해 조건식 사용
-- 주민번호 뒤 1번째(현재 데이터에서는 7번째) 값이 1,2 이면 앞에 19를 붙이고, 3,4 이면 20을 붙임. 
    -- 예를들어 9902052343233 이라면 1999년 02월 05일, 0205033431235 이라면 2002년 05월 03일 로 표현해야 함. 
select to_char(
                (case when (substr(v_jumin, 7, 1)='1') -- 7번째 자리 값이 1 
                        or (substr(v_jumin, 7, 1)='2') -- 또는 7번째 자리 값이 2 
                      then to_date('19'|| substr(v_jumin, 1, 6)) -- 7번째 자리 값이 1, 2면 19를 붙임. 
                      else to_date('20'|| substr(v_jumin, 1, 6)) end -- 그렇지 않으면 20을 붙임. 
                 )-- 여기까지가 to_char의 첫번째 값 부분.(생년월일을 8자리로 만드는 부분)
                , 'YYYY"년" MM"월" DD"일생"' -- 8자리로 만든 값으로 보여주고 싶은 형식
               ) as "생년월일" -- to_char가 여기서 끝남. 
            from tbl_vote_202005; 

-- 주민번호 처음 2자리로 4자리 년도 만들고, 3,4번째 값으로 00월 만들고, 5,6번째 값으로 00일 만드는 방식             
select case when    (substr(v_jumin, 7, 1)='1') 
                     or (substr(v_jumin, 7, 1)='2') 
                      then '19'|| substr(v_jumin, 1, 2) ||'년' 
                      else '20'|| substr(v_jumin, 1, 2) ||'년' end -- 여기까지 년도 
        || substr(v_jumin, 3, 2) || '월' || substr(v_jumin, 5, 2) || '일생' -- 00월 00일생 이어 붙이기 
        as 생년월일 -- 조회시 컬럼 이름을 생년월일로 보는 역할(없어도 됨)
            from tbl_vote_202005; 

-- 만 나이 계산하기 
-- 현재시간 년도만 뽑아내기 
select to_number(to_char(sysdate, 'yyyy')) from tbl_vote_202005;
-- 주민번호 앞자리 정보로 생년월일을 4자리로 만든 후 현재 년도에서 빼면 만 나이. 
-- 생년월일 4자리 만드는 건 위에서 작업한 쿼리문 응용 
select 
    to_char(
                (case when (substr(v_jumin, 7, 1)='1') -- 7번째 자리 값이 1 
                        or (substr(v_jumin, 7, 1)='2') -- 또는 7번째 자리 값이 2 
                      then '19'|| substr(v_jumin, 1, 2) -- 7번째 자리 값이 1, 2면 19를 붙임. 
                      else '20'|| substr(v_jumin, 1, 2) end -- 그렇지 않으면 20을 붙임. 
             )) as "출생년도(문자)" from tbl_vote_202005;
-- 뺄셈을 위해 위 쿼리에서 select 한 값을 숫자로 변환 
select 
    to_number(to_char(
                (case when (substr(v_jumin, 7, 1)='1') 
                        or (substr(v_jumin, 7, 1)='2') 
                      then '19'|| substr(v_jumin, 1, 2)
                      else '20'|| substr(v_jumin, 1, 2) end 
             ))) as "출생년도(숫자)" from tbl_vote_202005;
-- 현재년도에서 뺄셈 
select 
        to_number(to_char(sysdate, 'yyyy')) - 
        to_number(to_char(
                (case when (substr(v_jumin, 7, 1)='1') 
                        or (substr(v_jumin, 7, 1)='2') 
                      then '19'|| substr(v_jumin, 1, 2)
                      else '20'|| substr(v_jumin, 1, 2) end 
             ))) as "출생년도(숫자)" from tbl_vote_202005;
-- 만 00세로 표현
select 
        '만 ' || (to_number(to_char(sysdate, 'yyyy')) - 
        to_number(to_char(
                (case when (substr(v_jumin, 7, 1)='1') 
                        or (substr(v_jumin, 7, 1)='2') 
                      then '19'|| substr(v_jumin, 1, 2)
                      else '20'|| substr(v_jumin, 1, 2) end 
             )))) || '세' as "출생년도(숫자)" from tbl_vote_202005;

-- 성별 표시 
select case when substr(v_jumin, 7, 1)='1' or substr(v_jumin, 7, 1)='3' then '남'
            when substr(v_jumin, 7, 1)='2' or substr(v_jumin, 7, 1)='4' then '여'             
        end as v_gender from tbl_vote_202005;

-- 투표시간 0930 을 09:30으로 표현 
select substr(v_time, 1, 2) || ':' || substr(v_time, 3, 2) as "투표시간" from tbl_vote_202005;

-- 유권자 확인 Y는 확인, N은 미확인 으로 표현
select case when v_confirm='Y' then '확인'
            when v_confirm='N' then '미확인' 
       end as v_confirm
    from tbl_vote_202005;

-- 위에서 확인한 것으로 모두 한번에 조회(유권자이름, 생년월일, 나이, 성별, 후보번호, 투표시간, 유권자확인)
select v_name, -- 유권자이름
        to_char(
                (case when (substr(v_jumin, 7, 1)='1')
                        or (substr(v_jumin, 7, 1)='2')
                      then to_date('19'|| substr(v_jumin, 1, 6)) 
                      else to_date('20'|| substr(v_jumin, 1, 6)) end 
                 )
                , 'YYYY"년" MM"월" DD"일생"' 
               ) as "생년월일", -- 생년월일
        '만 ' || (to_number(to_char(sysdate, 'yyyy')) - 
        to_number(to_char(
                (case when (substr(v_jumin, 7, 1)='1') 
                        or (substr(v_jumin, 7, 1)='2') 
                      then '19'|| substr(v_jumin, 1, 2)
                      else '20'|| substr(v_jumin, 1, 2) end 
             )))) || '세' as "출생년도(숫자)", -- 나이
        case when substr(v_jumin, 7, 1)='1' or substr(v_jumin, 7, 1)='3' then '남'
            when substr(v_jumin, 7, 1)='2' or substr(v_jumin, 7, 1)='4' then '여' end, -- 성별     
        m_no, -- 후보번호
        substr(v_time, 1, 2) || ':' || substr(v_time, 3, 2) as "투표시간", -- 투표시간
        case when v_confirm='Y' then '확인'
            when v_confirm='N' then '미확인' 
       end as v_confirm -- 유권자확인
       from tbl_vote_202005;

-- 문제에서 제1투표장에서 한 것만 조회하라고 하였으므로 where 절 추가 
select v_name, -- 유권자이름
        to_char(
                (case when (substr(v_jumin, 7, 1)='1')
                        or (substr(v_jumin, 7, 1)='2')
                      then to_date('19'|| substr(v_jumin, 1, 6)) 
                      else to_date('20'|| substr(v_jumin, 1, 6)) end 
                 )
                , 'YYYY"년" MM"월" DD"일생"' 
               ) as 생년월일,
        '만 ' || (to_number(to_char(sysdate, 'yyyy')) - 
        to_number(to_char(
                (case when (substr(v_jumin, 7, 1)='1') 
                        or (substr(v_jumin, 7, 1)='2') 
                      then '19'|| substr(v_jumin, 1, 2)
                      else '20'|| substr(v_jumin, 1, 2) end 
             )))) || '세' as 출생년도, -- 나이
        case when substr(v_jumin, 7, 1)='1' or substr(v_jumin, 7, 1)='3' then '남'
        when substr(v_jumin, 7, 1)='2' or substr(v_jumin, 7, 1)='4' then '여' end, -- 성별
        m_no, -- 후보번호
        substr(v_time, 1, 2) || ':' || substr(v_time, 3, 2) as 투표시간, -- 투표시간
        case when v_confirm='Y' then '확인'
            when v_confirm='N' then '미확인' 
       end as v_confirm -- 유권자확인
       from tbl_vote_202005 where v_area='제1투표장';        

-- 생년월일 만드는 부분을 다르게 한 버전 
select v_name, -- 유권자이름
         case when    (substr(v_jumin, 7, 1)='1') 
                     or (substr(v_jumin, 7, 1)='2') 
                      then '19'|| substr(v_jumin, 1, 2) ||'년' 
                      else '20'|| substr(v_jumin, 1, 2) ||'년' end -- 여기까지 년도 
        || substr(v_jumin, 3, 2) || '월' || substr(v_jumin, 5, 2) || '일생' as 생년월일, 
        '만 ' || (to_number(to_char(sysdate, 'yyyy')) - 
        to_number(to_char(
                (case when (substr(v_jumin, 7, 1)='1') 
                        or (substr(v_jumin, 7, 1)='2') 
                      then '19'|| substr(v_jumin, 1, 2)
                      else '20'|| substr(v_jumin, 1, 2) end 
             )))) || '세' as 나이, -- 만나이
        case when substr(v_jumin, 7, 1)='1' or substr(v_jumin, 7, 1)='3' then '남'
        when substr(v_jumin, 7, 1)='2' or substr(v_jumin, 7, 1)='4' then '여' end, -- 성별             
        m_no, -- 후보번호
        substr(v_time, 1, 2) || ':' || substr(v_time, 3, 2) as 투표시간, -- 투표시간
        case when v_confirm='Y' then '확인'
            when v_confirm='N' then '미확인' 
       end as v_confirm -- 유권자확인
       from tbl_vote_202005 where v_area='제1투표장';

select * from tbl_vote_202005;
select * from tbl_member_202005;

-- 후보자 등수 조회
-- 후보번호별 투표건수 조회(group by) 
select count(m_no) from tbl_vote_202005 where v_confirm='Y' group by m_no ;
-- 후보번호, 후보이름, 건수 같이 조회(조인)
select m.m_no, m.m_name, count(v.m_no) as v_count 
    from tbl_vote_202005 v, tbl_member_202005 m 
    where m.m_no=v.m_no and v.v_confirm='Y' 
    group by m.m_no, m.m_name 
    order by v_count desc;

```