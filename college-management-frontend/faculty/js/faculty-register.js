document
  .getElementById("registerForm")
  .addEventListener("submit", async (e) => {
    e.preventDefault();

    const faculty = {
      name: document.getElementById("name").value.trim(),
      email: document.getElementById("email").value.trim(),
      contact: document.getElementById("contact").value.trim(),
      password: document.getElementById("password").value.trim(),
      department: document.getElementById("department").value.trim(),
    };

    try {
      const response = await fetch(
        "http://localhost:8080/api/faculty/register",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(faculty),
        }
      );

      if (response.ok) {
        const msg = await response.text();
        alert("✅ Faculty registered successfully!\n" + msg);
        window.location.href = "login.html";
      } else {
        const err = await response.text();
        alert("❌ Registration failed: " + err);
      }
    } catch (error) {
      console.error("Error registering faculty:", error);
      alert("Something went wrong. Check console for details.");
    }
  });
