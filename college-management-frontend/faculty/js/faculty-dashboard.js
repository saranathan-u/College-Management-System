async function loadFacultyProfile() {
  const token = localStorage.getItem("token");

  if (!token) {
    alert("⚠️ Not logged in. Redirecting to login...");
    window.location.href = "login.html";
    return;
  }

  try {
    const response = await fetch("http://localhost:8080/api/faculty/me", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (response.ok) {
      const faculty = await response.json();
      console.log("✅ Faculty data:", faculty);

      // Fill details in HTML
      document.getElementById("facultyName").textContent = faculty.name;
      document.getElementById("facultyEmail").textContent = faculty.email;
      document.getElementById("facultyDepartment").textContent =
        faculty.department;
      document.getElementById("facultyContact").textContent = faculty.contact;
    } else {
      const err = await response.text();
      console.error("❌ Failed to load profile:", err);
      alert("Failed to load profile. Please login again.");
      localStorage.removeItem("token");
      window.location.href = "login.html";
    }
  } catch (error) {
    console.error("🚨 Error loading profile:", error);
    alert("Something went wrong!");
  }
}

// Logout button
document.getElementById("logoutBtn").addEventListener("click", () => {
  localStorage.removeItem("token");
  window.location.href = "login.html";
});

loadFacultyProfile();
