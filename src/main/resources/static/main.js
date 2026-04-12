async function uploadFile() {
    const fileInput = document.getElementById("fileInput");
    if (!fileInput.files.length) {
        alert("Please select a file");
        return;
    }

    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append("file", file);

    const resultElement = document.getElementById("result");
    resultElement.innerText = "Generating tests...";

    try {
        const response = await fetch("/api/tests/generate", {
            method: "POST",
            body: formData
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.statusText}`);
        }

        const data = await response.json();
        resultElement.innerText = data.generatedTests;
    } catch (err) {
        resultElement.innerText = `Error: ${err.message}`;
    }
}

function showPage(pageId) {
    document.querySelectorAll('.page').forEach(p => p.style.display = 'none');
    document.getElementById(pageId).style.display = 'block';
}


async function uploadFile() {
    const fileInput = document.getElementById("fileInput");
    if (!fileInput.files.length) return alert("Please select a Python file");

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);

    const resultElement = document.getElementById("result");
    resultElement.innerText = "Generating tests...";

    try {
        const response = await fetch("/api/tests/generate", { method: "POST", body: formData });
        const data = await response.json();
        resultElement.innerText = data.generatedTests || "Success!";
    } catch (err) {
        resultElement.innerText = `Error: ${err.message}`;
    }
}