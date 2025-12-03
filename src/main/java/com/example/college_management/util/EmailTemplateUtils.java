package com.example.college_management.util;

public class EmailTemplateUtils {

    public static String getRegistrationEmail(String studentName, String username) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>Welcome to College Management System</h3>");
        sb.append("<p>Dear ").append(studentName).append(",</p>");
        sb.append("<p>Your account has been successfully created.</p>");
        sb.append("<p><strong>Username:</strong> ").append(username).append("</p>");
        sb.append("<p>Please keep your login details safe.</p>");
        sb.append("<p>Regards,<br>College Admin</p>");
        return sb.toString();
    }

    public static String getCourseEnrollmentEmail(String studentName, String courseTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>Course Enrollment Confirmation</h3>");
        sb.append("<p>Dear ").append(studentName).append(",</p>");
        sb.append("<p>You have successfully enrolled in the course: <strong>").append(courseTitle).append("</strong></p>");
        sb.append("<p>Best of luck!</p>");
        sb.append("<p>Regards,<br>College Admin</p>");
        return sb.toString();
    }

    public static String getResultNotificationEmail(String studentName, String courseTitle, String grade) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>Exam Result Notification</h3>");
        sb.append("<p>Dear ").append(studentName).append(",</p>");
        sb.append("<p>Your result for the course <strong>").append(courseTitle).append("</strong> is: <strong>").append(grade).append("</strong></p>");
        sb.append("<p>Regards,<br>College Admin</p>");
        return sb.toString();
    }
}
