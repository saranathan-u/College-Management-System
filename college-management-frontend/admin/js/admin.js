// Load supplier items
async function loadSuppliers() {
  const token = localStorage.getItem("adminToken");
  try {
    const res = await fetch("http://localhost:8080/api/admin/suppliers", {
      headers: { Authorization: `Bearer ${token}` },
    });
    const items = await res.json();
    const container = document.querySelector(".supplier-section");
    container.innerHTML = "";

    items.forEach((item) => {
      const div = document.createElement("div");
      div.classList.add("supplier-card");
      div.innerHTML = `
                <h3>${item.name}</h3>
                <p>${item.description}</p>
                <p>Price: ${item.price}</p>
            `;
      container.appendChild(div);
    });
  } catch (err) {
    console.error(err);
  }
}
loadSuppliers();
