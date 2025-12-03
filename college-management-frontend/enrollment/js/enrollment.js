// enrollment.js — updated for backend endpoint structure
const API_BASE = "http://localhost:8080/api/enrollments/enroll";

// helper: safe JSON parse
async function safeParseJson(response) {
  try {
    return await response.json();
  } catch (e) {
    console.warn("Could not parse JSON response:", e);
    return null;
  }
}

async function loadEnrollments() {
  console.log("➡️ loadEnrollments() called");
  const token = localStorage.getItem("token");
  const studentId = localStorage.getItem("studentId");

  const table = document.getElementById("enrollmentsTable");
  if (!table) {
    console.error("Missing element: #enrollmentsTable");
    return;
  }

  if (!token || !studentId) {
    table.innerHTML =
      "<tr><td colspan='4'>Please log in as a student to see enrollments.</td></tr>";
    return;
  }

  try {
    const url = "http://localhost:8080/api/enrollments/my";
    console.log("GET", url);
    const response = await fetch(url, {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!response.ok) {
      const txt = await response.text().catch(() => "No response body");
      throw new Error(
        `Failed to load enrollments (status ${response.status}): ${txt}`
      );
    }

    const enrollments = (await safeParseJson(response)) || [];
    table.innerHTML = `
      <tr>
        <th>Enrollment ID</th>
        <th>Course Title</th>
        <th>Credits</th>
        <th>Duration</th>
      </tr>
    `;
    if (enrollments.length === 0) {
      table.insertRow().innerHTML = `<td colspan="4">No enrollments found.</td>`;
      return;
    }

    enrollments.forEach((enrollment) => {
      const row = table.insertRow();
      const course = enrollment.course || {};
      row.innerHTML = `
        <td>${enrollment.id ?? "-"}</td>
        <td>${course.title ?? "N/A"}</td>
        <td>${course.credits ?? "-"}</td>
        <td>${course.duration ?? "-"}</td>
      `;
    });
  } catch (err) {
    console.error("Error loading enrollments:", err);
    table.innerHTML = `<tr><td colspan="4">Error loading enrollments. Check console.</td></tr>`;
  }
}

// ✅ updated enrollStudent() — matches /api/students/enroll?courseId=
async function enrollStudent(courseId) {
  const token = localStorage.getItem("token");
  if (!token) {
    alert("Please log in first.");
    window.location.href = "../student/login.html";
    return;
  }

  console.log("➡️ Enrolling in course:", courseId);

  try {
    const response = await fetch(API_BASE, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ courseId: Number(courseId) }),
    });

    const body =
      (await safeParseJson(response)) ||
      (await response.text().catch(() => null));
    console.log("📬 enroll response:", response.status, body);

    if (response.ok) {
      alert("✅ Enrolled successfully!");
      await loadEnrollments();
    } else {
      let msg;
      if (typeof body === "string") {
        msg = body;
      } else if (body && body.message) {
        msg = body.message;
      } else {
        msg = JSON.stringify(body);
      }
      alert("❌ Enrollment failed: " + msg);
    }
  } catch (err) {
    console.error("Network/Fetch error enrolling student:", err);
    alert("⚠️ Could not enroll: " + err.message);
  }
}

document.addEventListener("DOMContentLoaded", () => {
  console.log("✅ enrollment.js loaded");

  const form = document.getElementById("enrollmentForm");
  const courseInput = document.getElementById("courseId");

  if (!form) {
    console.error("Missing element: #enrollmentForm");
    return;
  }
  if (!courseInput) {
    console.error("Missing element: #courseId");
    return;
  }

  // handle submit
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    console.log("🎯 Enroll button clicked");

    const courseId = courseInput.value.trim();
    if (!courseId) {
      alert("Course ID is required.");
      return;
    }

    await enrollStudent(courseId);
  });

  loadEnrollments();
});
