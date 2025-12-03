document.addEventListener("DOMContentLoaded", async () => {
  const token = localStorage.getItem("adminToken"); // JWT from login

  try {
    const res = await fetch("http://localhost:8080/api/suppliers/items", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (!res.ok) throw new Error("Failed to fetch supplier items");

    const items = await res.json();
    const table = document.getElementById("supplierTable");

    // Clear existing rows (except header)
    table.innerHTML = `
      <tr>
        <th>Item Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Supplier</th>
      </tr>
    `;

    items.forEach((item) => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${item.name}</td>
        <td>${item.description}</td>
        <td>${item.price}</td>
        <td>${item.supplierName}</td>
      `;
      table.appendChild(row);
    });
  } catch (err) {
    console.error(err);
    alert("Error fetching supplier items. Try again later.");
  }
});
