async function loadStudentProfile() {
  const token = localStorage.getItem("token");

  if (!token) {
    alert("⚠️ Not logged in. Redirecting to login...");
    window.location.href = "login.html";
    return;
  }

  try {
    const response = await fetch("http://localhost:8080/api/students/me", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (response.ok) {
      const student = await response.json();
      console.log("✅ Student data:", student);

      document.getElementById("studentName").textContent = student.name;
      document.getElementById("studentEmail").textContent = student.email;
      document.getElementById("studentContact").textContent = student.contact;
      document.getElementById("studentRoll").textContent = student.rollNo;
      document.getElementById("studentDept").textContent = student.department;

      // Profile photo
      const photo = document.getElementById("studentPhoto");
      photo.src = student.imageUrl || "images/default-avatar.png";
      photo.alt = student.name;

      // Pre-fill update form
      document.getElementById("updateName").value = student.name;
      document.getElementById("updateContact").value = student.contact;
      document.getElementById("updateDept").value = student.department;
      document.getElementById("updateRoll").value = student.rollNo;
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

document.getElementById("logoutBtn").addEventListener("click", () => {
  localStorage.removeItem("token");
  window.location.href = "login.html";
});

document.getElementById("updateForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const token = localStorage.getItem("token");

  const name = document.getElementById("updateName").value;
  const contact = document.getElementById("updateContact").value;
  const department = document.getElementById("updateDept").value;
  const rollNo = document.getElementById("updateRoll").value;
  const file = document.getElementById("updatePhoto").files[0];

  const formData = new FormData();
  formData.append("name", name);
  formData.append("contact", contact);
  formData.append("department", department);
  formData.append("rollNo", rollNo);
  if (file) formData.append("file", file);

  try {
    const response = await fetch(
      "http://localhost:8080/api/students/me/update-profile",
      {
        method: "POST",
        headers: { Authorization: `Bearer ${token}` },
        body: formData,
      }
    );

    if (response.ok) {
      alert("✅ Profile updated successfully!");
      loadStudentProfile();
    } else {
      const err = await response.text();
      alert("❌ Update failed: " + err);
    }
  } catch (error) {
    console.error("🚨 Error updating profile:", error);
    alert("Something went wrong!");
  }
});

loadStudentProfile();

function goToEnroll() {
  console.log("Redirecting to enrollment page...");
  window.location.href = "../enrollment/enrollment.html";
}

function goToUpdateProfile() {
  window.location.href = "../student/update-profile.html";
}
