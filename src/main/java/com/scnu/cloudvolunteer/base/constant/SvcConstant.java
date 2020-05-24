package com.scnu.cloudvolunteer.base.constant;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:19:50
 * @description：
 *  服务码常量
 */
public class SvcConstant {
    private static final String USER_MODEL = "10";
    private static final String COURSE_MODEL = "20";
    private static final String PAPER_MODEL = "30";
    private static final String ANSWER_MODEL = "40";
    private static final String QUESTION_MODEL = "50";

    /*----------------------------用户模块-----------------------------------*/

    public static final String USER_LOGIN = USER_MODEL + "10";
    public static final String USER_SETTING = USER_MODEL + "20";
    public static final String STUDENT_QUERY = USER_MODEL + "30";
    public static final String TEACHER_QUERY = USER_MODEL + "40";

    /*----------------------------课程模块-----------------------------------*/

    public static final String OPEN_COURSE = COURSE_MODEL + "10";
    public static final String JOIN_COURSE = COURSE_MODEL + "20";
    public static final String QUIT_COURSE = COURSE_MODEL + "30";
    public static final String SETTING_COURSE = COURSE_MODEL + "40";
    public static final String STUDENT_STATISTIC_COURSE = COURSE_MODEL + "50";
    public static final String TEACHER_STATISTIC_COURSE = COURSE_MODEL + "60";
    public static final String OPEN_QUERY_COURSE = COURSE_MODEL + "15";
    public static final String SELECT_QUERY_COURSE = COURSE_MODEL + "25";

    /*----------------------------试卷模块-----------------------------------*/

    public static final String NEW_PAPER = PAPER_MODEL + "10";
    public static final String SETTING_PAPER = PAPER_MODEL + "20";
    public static final String PUBLISH_PAPER = PAPER_MODEL + "30";
    public static final String GET_PAPER = PAPER_MODEL + "40";

    public static final String PAPERS_QUERY = PAPER_MODEL + "15";
    public static final String PAPER_QUERY = PAPER_MODEL + "16";
    public static final String PUBLISH_QUERY = PAPER_MODEL + "35";

    /*----------------------------答卷模块-----------------------------------*/

    public static final String ANSWER = ANSWER_MODEL + "10";
    public static final String ANSWER_STATISTIC = ANSWER_MODEL + "20";
    public static final String TEXT_ANSWER = ANSWER_MODEL + "30";

    public static final String ANSWERS_QUERY = ANSWER_MODEL + "15";
    public static final String ANSWER_QUERY = ANSWER_MODEL + "16";
    public static final String TEXT_QUERY = ANSWER_MODEL + "35";

    /*----------------------------试题模块-----------------------------------*/

    public static final String HAND_UPLOAD = QUESTION_MODEL + "10";
    public static final String WORD_UPLOAD = QUESTION_MODEL + "20";
    public static final String EXCEL_UPLOAD = QUESTION_MODEL + "30";
    public static final String QUESTION_QUERY = QUESTION_MODEL + "40";
    public static final String EDIT_QUESTION = QUESTION_MODEL + "50";
    public static final String DELETE_QUESTION = QUESTION_MODEL + "60";


    /**
     * QueryService用户所有信息查询服务，如试卷，个人信息
     */
    public static final String QUERY = "0000";
}
