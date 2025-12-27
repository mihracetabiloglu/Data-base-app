const token = localStorage.getItem("token");

function searchBook() {
  const name = document.getElementById("search").value;

  fetch(`http://localhost:8080/api/books/search?name=${name}`, {
    headers: {
      "Authorization": "Bearer " + token
    }
  })
  .then(res => res.json())
  .then(data => {
    const list = document.getElementById("bookList");
    list.innerHTML = "";

    data.forEach(book => {
      const li = document.createElement("li");
      li.innerHTML = `
        ${book.title}
        <button onclick="borrow(${book.id})">Ödünç Al</button>
      `;
      list.appendChild(li);
    });
  });
}

function borrow(bookId) {
  fetch(`http://localhost:8080/api/loans/borrow?bookId=${bookId}&userId=1`, {
    method: "POST",
    headers: {
      "Authorization": "Bearer " + token
    }
  })
  .then(res => res.text())
  .then(alert);
}
async function searchBook() {
    const name = document.getElementById('searchInput').value;
    if (name.length < 2) { loadBooks(); return; } // Arama kutusu boşsa tümünü getir

    const response = await fetch(`http://localhost:8080/api/books/search?name=${name}`);
    const books = await response.json();
    
    // Kitap kartlarını güncelleyen kod... (daha önce yazdığımız innerHTML kısmı)
    displayBooks(books); 
}