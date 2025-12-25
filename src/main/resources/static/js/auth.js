function login() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  fetch("http://localhost:8080/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password })
  })
  .then(res => {
    if (!res.ok) throw new Error("Giriş başarısız");
    return res.json();
  })
  .then(data => {
    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);

    if (data.role === "ROLE_ADMIN") {
      window.location.href = "admin.html";
    } else {
      window.location.href = "user.html";
    }
  })
  .catch(err => {
    document.getElementById("error").innerText = err.message;
  });
}
