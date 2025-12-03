const token =
  localStorage.getItem("facultyToken") || localStorage.getItem("studentToken");

// ==================== ADD NEW EXAM (Faculty) ====================
const examForm = document.getElementById("examForm");
if (examForm) {
  examForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
      name: document.getElementById("examName").value,
      date: document.getElementById("examDate").value,
      courseId: document.getElementById("courseId").value,
      duration: 90, // Default duration, you can add input in HTML
    };

    try {
      const res = await fetch("http://localhost:8080/api/exams", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });

      if (res.ok) {
        alert("Exam added successfully!");
        loadExams();
        examForm.reset();
      } else {
        alert("Failed to add exam");
      }
    } catch (err) {
      console.error("Error adding exam:", err);
    }
  });
}

// ==================== LOAD ALL EXAMS ====================
async function loadExams() {
  try {
    const res = await fetch("http://localhost:8080/api/exams", {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!res.ok) throw new Error("Failed to fetch exams");

    const exams = await res.json();
    const table = document.getElementById("examsTable");

    // Reset table with headers
    table.innerHTML = `
      <tr>
        <th>Exam ID</th>
        <th>Exam Name</th>
        <th>Date</th>
        <th>Course</th>
      </tr>
    `;

    exams.forEach((exam) => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${exam.id}</td>
        <td>${exam.name}</td>
        <td>${exam.date}</td>
        <td>${exam.course ? exam.course.title : "N/A"}</td>
      `;
      table.appendChild(row);
    });
  } catch (err) {
    console.error("Error loading exams:", err);
  }
}
loadExams();

// ==================== ADD RESULT (Faculty) ====================
const resultForm = document.getElementById("resultForm");
if (resultForm) {
  resultForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const examId = document.getElementById("examId").value;
    const payload = {
      studentId: document.getElementById("studentId").value,
      grade: document.getElementById("grade").value,
    };

    try {
      const res = await fetch(
        `http://localhost:8080/api/exams/${examId}/result`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(payload),
        }
      );

      if (res.ok) {
        alert("Result added successfully!");
        resultForm.reset();
      } else {
        alert("Failed to add result");
      }
    } catch (err) {
      console.error("Error adding result:", err);
    }
  });
}

// ==================== FETCH RESULTS BY STUDENT (Student/Admin) ====================
const fetchResultsForm = document.getElementById("fetchResultsForm");
if (fetchResultsForm) {
  fetchResultsForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const studentId = document.getElementById("resultStudentId").value;

    try {
      const res = await fetch(
        `http://localhost:8080/api/exams/results/${studentId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (!res.ok) throw new Error("Failed to fetch results");

      const results = await res.json();
      const table = document.getElementById("resultsTable");

      // Reset table with headers
      table.innerHTML = `
        <tr>
          <th>Result ID</th>
          <th>Exam</th>
          <th>Grade</th>
        </tr>
      `;

      results.forEach((result) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${result.id}</td>
          <td>${result.exam ? result.exam.name : "N/A"}</td>
          <td>${result.grade}</td>
        `;
        table.appendChild(row);
      });
    } catch (err) {
      console.error("Error fetching results:", err);
    }
  });
}
