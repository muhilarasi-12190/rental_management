javascript
function showSection(sectionName) {
    document.getElementById("resources").hidden = true;
    document.getElementById("customers").hidden = true;
    document.getElementById("rentals").hidden = true;

    document.getElementById(sectionName).hidden = false;
}

// Add Resource
document.getElementById("resourceForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const resource = {
        name: document.getElementById("resourceName").value,
        category: document.getElementById("resourceCategory").value,
        rentalPrice: Number(document.getElementById("resourcePrice").value)
    };

    const response = await fetch("/resources", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(resource)
    });

    if (response.ok) {
        alert("Resource added successfully");
        this.reset();
        loadResources();
    } else {
        alert("Could not add resource. Check the details.");
    }
});

// Add Customer
document.getElementById("customerForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const customer = {
        name: document.getElementById("customerName").value,
        phone: document.getElementById("customerPhone").value
    };

    const response = await fetch("/customers", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(customer)
    });

    if (response.ok) {
        alert("Customer added successfully");
        this.reset();
        loadCustomers();
    } else {
        alert("Could not add customer. Check the details.");
    }
});

// Add Rental
document.getElementById("rentalForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const rental = {
        customer: {
            id: Number(document.getElementById("rentalCustomerId").value)
        },
        resource: {
            id: Number(document.getElementById("rentalResourceId").value)
        },
        rentalDate: document.getElementById("rentalDate").value,
        returnDate: document.getElementById("returnDate").value || null
    };

    const response = await fetch("/rentals", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(rental)
    });

    if (response.ok) {
        alert("Rental added successfully");
        this.reset();
        loadRentals();
    } else {
        alert("Could not add rental. Check the customer and resource IDs.");
    }
});

// Display Resources
async function loadResources() {
    const response = await fetch("/resources");
    const resources = await response.json();

    document.getElementById("resourceList").innerHTML =
        resources.map(resource =>
            `<p>ID: ${resource.id} | ${resource.name} | ${resource.category} | Price: ${resource.rentalPrice}</p>`
        ).join("");
}

// Display Customers
async function loadCustomers() {
    const response = await fetch("/customers");
    const customers = await response.json();

    document.getElementById("customerList").innerHTML =
        customers.map(customer =>
            `<p>ID: ${customer.id} | ${customer.name} | Phone: ${customer.phone}</p>`
        ).join("");
}

// Display Rentals
async function loadRentals() {
    const response = await fetch("/rentals");
    const rentals = await response.json();

    document.getElementById("rentalList").innerHTML =
        rentals.map(rental =>
            `<p>Rental ID: ${rental.id} | Customer ID: ${rental.customer.id} | Resource ID: ${rental.resource.id} | Rental Date: ${rental.rentalDate} | Return Date: ${rental.returnDate || "Not returned"}</p>`
        ).join("");
}

// Load records when the page opens
loadResources();
loadCustomers();
loadRentals();

