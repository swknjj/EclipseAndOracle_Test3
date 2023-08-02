package com.example.test3withcontroller.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoteDAO {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe", "user1", "1234");
        return con;
    }

    // 후보조회 화면을 위한 처리
    public List<MemberDTO> memberList() throws Exception {
        // 후보자 목록을 가져올 때 정당이름도 보여줘야 하기 때문에 후보자 테이블, 정당 테이블 조인(p_code 기준으로)
        // 쿼리문이 길수록 띄어쓰기 주의!!
        String sql = "select m.m_no, m.m_name, p.p_name, decode(m.p_school, '1','고졸', '2','학사', '3','석사', '4','박사') p_school, " +
                        "substr(m.m_jumin, 1, 6) || '-' || substr(m.m_jumin, 7, 7) " +
                        "m_jumin, m_city, " +
                        "p.p_tel1 ||'-'|| p.p_tel2 ||'-'|| p.p_tel3 p_tel " +
                        "from tbl_member_202005 m, tbl_party_202005 p where m.p_code=p.p_code";
        List<MemberDTO> memberList = new ArrayList<>();
        con = getConnection();
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setM_no(rs.getString(1));
            memberDTO.setM_name(rs.getString(2));
            memberDTO.setP_name(rs.getString(3));
            memberDTO.setP_school(rs.getString(4));
            memberDTO.setM_jumin(rs.getString(5));
            memberDTO.setM_city(rs.getString(6));
            memberDTO.setP_tel(rs.getString(7));
            memberList.add(memberDTO);
        }
        return memberList;
    }

    public int saveVote(String v_jumin, String v_name, String m_no, String v_time, String v_area, String v_confirm) throws Exception {
        con = getConnection();
        String sql = "insert into tbl_vote_202005 values(?,?,?,?,?,?)";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, v_jumin);
        pstmt.setString(2, v_name);
        pstmt.setString(3, m_no);
        pstmt.setString(4, v_time);
        pstmt.setString(5, v_area);
        pstmt.setString(6, v_confirm);
        int result = pstmt.executeUpdate();
        return result;
    }


    public List<VoteDTO> voteList() throws Exception {
        con = getConnection();
        String sql = "select v_name, " +
                "         case when    (substr(v_jumin, 7, 1)='1') " +
                "                     or (substr(v_jumin, 7, 1)='2') " +
                "                      then '19'|| substr(v_jumin, 1, 2) ||'년' " +
                "                      else '20'|| substr(v_jumin, 1, 2) ||'년' end " +
                "        || substr(v_jumin, 3, 2) || '월' || substr(v_jumin, 5, 2) || '일생' as 생년월일, " +
                "        '만 ' || (to_number(to_char(sysdate, 'yyyy')) - " +
                "        to_number(to_char(" +
                "                (case when (substr(v_jumin, 7, 1)='1') " +
                "                        or (substr(v_jumin, 7, 1)='2') " +
                "                      then '19'|| substr(v_jumin, 1, 2)" +
                "                      else '20'|| substr(v_jumin, 1, 2) end " +
                "             )))) || '세' as 나이, " +
                "case when substr(v_jumin, 7, 1)='1' or substr(v_jumin, 7, 1)='3' then '남'\n" +
                "        when substr(v_jumin, 7, 1)='2' or substr(v_jumin, 7, 1)='4' then '여' end," +
                "        m_no, " +
                "        substr(v_time, 1, 2) || ':' || substr(v_time, 3, 2) as 투표시간, " +
                "        case when v_confirm='Y' then '확인'" +
                "            when v_confirm='N' then '미확인' " +
                "       end as v_confirm " +
                "       from tbl_vote_202005 where v_area='제1투표장'";
        List<VoteDTO> voteList = new ArrayList<>();
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            VoteDTO voteDTO = new VoteDTO();
            voteDTO.setV_name(rs.getString(1));
            voteDTO.setV_birth(rs.getString(2));
            voteDTO.setV_age(rs.getString(3));
            voteDTO.setV_gender(rs.getString(4));
            voteDTO.setM_no(rs.getString(5));
            voteDTO.setV_time(rs.getString(6));
            voteDTO.setV_confirm(rs.getString(7));
            voteList.add(voteDTO);
        }
        return voteList;

    }

    public List<RankingDTO> voteRanking() throws Exception {
        con = getConnection();
        String sql = "select m.m_no, m.m_name, count(v.m_no) as v_count " +
                    "    from tbl_vote_202005 v, tbl_member_202005 m " +
                    "    where m.m_no=v.m_no and v.v_confirm='Y' " +
                    "    group by m.m_no, m.m_name " +
                    "    order by v_count desc";
        List<RankingDTO> voteRanking = new ArrayList<>();
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            RankingDTO rankingDTO = new RankingDTO();
            rankingDTO.setM_no(rs.getString(1));
            rankingDTO.setM_name(rs.getString(2));
            rankingDTO.setV_count(rs.getInt(3));
            voteRanking.add(rankingDTO);
        }
        return voteRanking;
    }
}
