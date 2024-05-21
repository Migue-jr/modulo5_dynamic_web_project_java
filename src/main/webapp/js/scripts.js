document.addEventListener("DOMContentLoaded", function() {
    const userIdInput = document.getElementById("userId");
    const errorMessage = document.getElementById("error-message");
    const buttons = document.querySelectorAll("button[type='submit']");

    buttons.forEach(button => {
        button.addEventListener("click", function(event) {
            if (!userIdInput.value.trim()) {
                event.preventDefault();
                errorMessage.style.display = "block";
                errorMessage.textContent = "User ID is required";
            } else {
                errorMessage.style.display = "none";
            }
        });
    });
});
