document
  .getElementById("loginForm")
  .addEventListener("submit", async function (e) {
    e.preventDefault();

    const loginData = {
      usernameOrEmail: document.getElementById("loginEmail").value.trim(),
      password: document.getElementById("loginPassword").value.trim(),
    };

    const BACKEND_URL = "http://localhost:8080/api/students/login";

    try {
      const response = await fetch(BACKEND_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
      });

      if (response.ok) {
        const result = await response.json();
        localStorage.setItem("token", result.token || "");
        localStorage.setItem("email", loginData.usernameOrEmail);
        alert("✅ Login successful!");
        window.location.href = "dashboard.html";
      } else {
        let errorText = "Login failed. Please check your credentials.";
        try {
          const errorData = await response.json();
          if (errorData.message) errorText = errorData.message;
        } catch (jsonError) {
          console.warn("Could not parse backend error JSON");
        }
        alert("⚠️ " + errorText);
      }
    } catch (err) {
      console.error("❌ Error during login:", err);
      if (err.message.includes("Failed to fetch")) {
        alert(
          "🚫 Could not reach backend. Make sure Spring Boot is running on port 8080."
        );
      } else {
        alert("⚠️ Something went wrong. Please try again later.");
      }
    }
  });
