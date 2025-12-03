// Load upcoming exams
async function loadExams() {
  const token =
    localStorage.getItem("studentToken") ||
    localStorage.getItem("facultyToken");

  try {
    const res = await fetch("http://localhost:8080/api/exams", {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!res.ok) {
      throw new Error("Failed to fetch exams");
    }

    const exams = await res.json();
    const container = document.querySelector(".exams-section");
    container.innerHTML = "";

    if (exams.length === 0) {
      container.innerHTML = "<p>No upcoming exams found.</p>";
      return;
    }

    exams.forEach((exam) => {
      const div = document.createElement("div");
      div.classList.add("exam-card");
      div.innerHTML = `
        <h3>${exam.name}</h3>
        <p>Course: ${exam.course ? exam.course.title : "N/A"}</p>
        <p>Date: ${exam.date}</p>
        <p>Duration: ${exam.duration} mins</p>
      `;
      container.appendChild(div);
    });
  } catch (err) {
    console.error("Error loading exams:", err);
    document.querySelector(".exams-section").innerHTML =
      "<p>Could not load exams. Try again later.</p>";
  }
}

// Auto-load on page load
loadExams();
