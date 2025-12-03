async function loadNotifications() {
  const token = localStorage.getItem("studentToken");
  const studentId = localStorage.getItem("studentId");
  try {
    const res = await fetch(
      `http://localhost:8080/api/notifications/student/${studentId}`,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
    const notes = await res.json();
    const container = document.querySelector(".notifications-section");
    container.innerHTML = "";

    notes.forEach((note) => {
      const div = document.createElement("div");
      div.classList.add("notification-card");
      div.innerHTML = `<p>${note.message}</p>`;
      container.appendChild(div);
    });
  } catch (err) {
    console.error(err);
  }
}
loadNotifications();
