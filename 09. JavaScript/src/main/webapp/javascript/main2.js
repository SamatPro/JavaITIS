document.addEventListener("DOMContentLoaded", function(event) {
    console.log("DOM fully loaded and parsed");
    comment = document.getElementById("comment");
    comment.addEventListener('change', (event) => {
        console.log(comment.value)
    })
});




