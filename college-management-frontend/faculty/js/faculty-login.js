document
  .getElementById("facultyLoginForm")
  .addEventListener("submit", async (e) => {
    e.preventDefault();
    console.log("🚀 Faculty login triggered");

    const usernameOrEmail = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    try {
      const response = await fetch("http://localhost:8080/api/faculty/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ usernameOrEmail, password }),
      });

      console.log("🔗 Response received:", response.status);
      const text = await response.text();
      console.log("📦 Raw response:", text);

      if (response.ok) {
        const data = JSON.parse(text);
        console.log("✅ Parsed data:", data);

        localStorage.setItem("token", data.token);
        alert("✅ Login successful!");
        window.location.href = "dashboard.html";
      } else {
        alert("❌ Login failed: " + text);
      }
    } catch (err) {
      console.error("🚨 Error:", err);
      alert("Something went wrong. Please try again later!!");
    }
  });
