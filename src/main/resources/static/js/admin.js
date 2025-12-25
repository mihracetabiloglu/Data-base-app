const token = localStorage.getItem("token");

function addBook() {
  const title = document.getElementById("title").value;

  fetch("http://localhost:8080/admin/books", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + token
    },
    body: JSON.stringify({ title })
  })
  .then(res => res.text())
  .then(alert);
}
function deleteBook() {
  const bookId = document.getElementById("bookId").value;

  fetch(`http://localhost:8080/admin/books/${bookId}`, {
    method: "DELETE",
    headers: {
      "Authorization": "Bearer " + token
    }
  })
  .then(res => res.text())
  .then(alert);
}