document
  .getElementById("facultyLoginForm")
  ?.addEventListener("submit", async function (e) {
    e.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
      const res = await fetch("http://localhost:8080/api/auth/faculty/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (res.ok) {
        const data = await res.json();
        localStorage.setItem("facultyToken", data.token);
        alert("Login successful!");
        window.location.href = "dashboard.html";
      } else alert("Invalid credentials");
    } catch (err) {
      console.error(err);
      alert("Server error");
    }
  });
