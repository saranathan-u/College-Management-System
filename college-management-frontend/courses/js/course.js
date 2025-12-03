// ✅ course.js - Works with course cards (not table)
document.addEventListener("DOMContentLoaded", () => {
  console.log("✅ course.js loaded");

  const API_BASE = "http://localhost:8080/api/enrollments";
  const buttons = document.querySelectorAll(".enroll-btn");

  buttons.forEach((btn) => {
    btn.addEventListener("click", async () => {
      const courseId = btn.getAttribute("data-id");
      const token =
        localStorage.getItem("token") || localStorage.getItem("studentToken");
      const studentId = localStorage.getItem("studentId");

      if (!token || !studentId) {
        alert("⚠️ Please log in as a student before enrolling.");
        window.location.href = "../student/login.html";
        return;
      }

      try {
        console.log(`📘 Enrolling student ${studentId} in course ${courseId}`);

        const response = await fetch(API_BASE, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({ studentId, courseId }),
        });

        if (response.ok) {
          alert("✅ Enrolled successfully!");
        } else {
          const errorText = await response.text();
          alert("❌ Enrollment failed: " + errorText);
        }
      } catch (err) {
        console.error("Enrollment error:", err);
        alert("⚠️ Could not enroll. Check console for details.");
      }
    });
  });
});
