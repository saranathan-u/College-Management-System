// register.js

document
  .getElementById("registerForm")
  .addEventListener("submit", async function (e) {
    e.preventDefault();

    // Get form values
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const contact = document.getElementById("contact").value.trim();
    const password = document.getElementById("password").value.trim();
    const rollNo = document.getElementById("rollNo").value.trim();

    // ⚡ Create username automatically from rollNo or email
    const username = rollNo || email.split("@")[0];

    // Data object must match backend DTO
    const studentData = {
      usernameOrEmail: username, // for login
      name: name, // fixes "name cannot be null"
      email: email,
      contact: contact,
      password: password,
      rollNo: rollNo,
    };

    try {
      const response = await fetch(
        "http://localhost:8080/api/students/register",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(studentData),
        }
      );

      if (response.ok) {
        const result = await response.json();
        alert("Registration successful!");
        window.location.href = "login.html";
      } else {
        const errorData = await response.json();
        alert("Registration failed: " + (errorData.message || "Unknown error"));
      }
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred while registering. Please try again.");
    }
  });
