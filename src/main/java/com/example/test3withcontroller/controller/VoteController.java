package com.example.test3withcontroller.controller;

import com.example.test3withcontroller.model.MemberDTO;
import com.example.test3withcontroller.model.RankingDTO;
import com.example.test3withcontroller.model.VoteDAO;
import com.example.test3withcontroller.model.VoteDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class VoteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doProcess(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doProcess(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8"); // 한글 깨짐 방지 등을 위해
        String uri = request.getRequestURI(); // 요청한 주소값을 가져옴.(주소가 'localhost:8080/프로젝트이름/요청주소' 라면 '/프로젝트이름/요청주소' 를 가져옴.)
        int lastIndex = uri.lastIndexOf("/");  // '/' 가 마지막으로 있는 곳의 index 번호를 가져옴.
        String action = uri.substring(lastIndex); // 위에서 찾은 인덱스 이후의 값을 가져옴. (/요청주소)

        // DB 작업을 위한 DAO 클래스 객체 생성
        VoteDAO voteDAO = new VoteDAO();

        // 각 처리가 끝난 후 보여줄 화면이름 또는 주소
        String resultPage = "";
        if (action.equals("/memberList")) {
            // 후보자 조회 요청
            List<MemberDTO> memberList = voteDAO.memberList();
            request.setAttribute("memberList", memberList);
            resultPage = "memberList.jsp";
        } else if (action.equals("/voteForm")) {
            // 투표 화면 요청
            resultPage = "voteForm.jsp";
        } else if (action.equals("/vote")) {
            // 투표 처리
            String v_jumin = request.getParameter("v_jumin");
            String v_name = request.getParameter("v_name");
            String m_no = request.getParameter("m_no");
            String v_time = request.getParameter("v_time");
            String v_area = request.getParameter("v_area");
            String v_confirm = request.getParameter("v_confirm");

            int result = voteDAO.saveVote(v_jumin, v_name, m_no, v_time, v_area, v_confirm);
            request.setAttribute("result", result);
            resultPage = "vote.jsp";
        } else if (action.equals("/voteList")) {
            // 투표 검수 조회
            List<VoteDTO> voteList = voteDAO.voteList();
            request.setAttribute("voteList", voteList);
            resultPage = "voteList.jsp";
        } else if (action.equals("/voteRanking")) {
            // 후보자 등수
            List<RankingDTO> voteRanking = voteDAO.voteRanking();
            request.setAttribute("voteRanking", voteRanking);
            resultPage = "voteRanking.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage); // 보여줄 화면 또는 주소 지정
        dispatcher.forward(request, response); // 포워딩 실행(위에서 지정한 곳으로 request에 담은 데이터도 가져감)
    }
}
