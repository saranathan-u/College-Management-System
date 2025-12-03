document.addEventListener("DOMContentLoaded", async () => {
  try {
    // Fetch welcome message
    const welcomeRes = await fetch("http://localhost:8080/welcome");
    const welcomeText = await welcomeRes.text();
    document.getElementById("welcomeMessage").innerText = welcomeText;

    // Fetch about message
    const aboutRes = await fetch("http://localhost:8080/welcome/about");
    const aboutText = await aboutRes.text();
    document.getElementById("aboutMessage").innerText = aboutText;

    // Fetch contact info
    const contactRes = await fetch("http://localhost:8080/welcome/contact");
    const contactText = await contactRes.text();
    document.getElementById("contactMessage").innerText = contactText;
  } catch (error) {
    console.error("Error fetching data:", error);
    alert("Failed to fetch content from backend");
  }
});
