$(document).ready(() => {
    //const UPLOAD_URL = 'https://api.imgbb.com/1/upload?key=f15b7672b112aebce15692151faf7495'
    //const CURRENT = 'https://api.cloudinary.com/v1_1/dvwciugyu/image/upload'


    // ************************ Drag and drop ***************** //
    let dropArea = document.getElementById("drop-area");
    let datareturn = document.getElementById("datareturn");
    let urlreturn = document.getElementById("urlreturn");

    // Prevent default drag behaviors
    ;
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        dropArea.addEventListener(eventName, preventDefaults, false);
        document.body.addEventListener(eventName, preventDefaults, false);
    });

    // Highlight drop area when item is dragged over it
    ['dragenter', 'dragover'].forEach(eventName => {
        dropArea.addEventListener(eventName, highlight, false)
    });

    ['dragleave', 'drop'].forEach(eventName => {
        dropArea.addEventListener(eventName, unhighlight, false);
    });

    // Handle dropped files
    dropArea.addEventListener('drop', handleDrop, false);

    function preventDefaults(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    function highlight(e) {
        dropArea.classList.add('highlight');
    }

    function unhighlight(e) {
        dropArea.classList.remove('active');
    }

    function handleDrop(e) {
        var dt = e.dataTransfer;
        var files = dt.files;

        handleFiles(files);
    }

    let uploadProgress = [];
    let progressBar = document.getElementById('progress-bar');

    function initializeProgress(numFiles) {
        datareturn.style.display = "none";
        urlreturn.value = "";
        progressBar.value = 0;
        uploadProgress = [];

        for (let i = numFiles; i > 0; i--) {
            uploadProgress.push(0)
        }
    }

    function updateProgress(fileNumber, percent) {
        uploadProgress[fileNumber] = percent;
        let total = uploadProgress.reduce((tot, curr) => tot + curr, 0) / uploadProgress.length;
        console.debug('update', fileNumber, percent, total);
        progressBar.value = total
    }

    function handleFiles(files) {
        files = [...files];
        initializeProgress(files.length);
        files.forEach(uploadFile);
        files.forEach(previewFile);
    }

    function previewFile(file) {
        let reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = function() {
            let img = document.createElement('img');
            img.src = reader.result;
            document.getElementById('gallery').appendChild(img);
        }
    }

    function uploadFile(file, i) {
        var url = 'https://api.cloudinary.com/v1_1/dvwciugyu/image/upload';
        var xhr = new XMLHttpRequest();
        var formData = new FormData();
        xhr.open('POST', url, true);
        xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

        // Update progress (can be used to show progress indicator)
        xhr.upload.addEventListener("progress", function(e) {
            updateProgress(i, (e.loaded * 100.0 / e.total) || 100)
        });

        xhr.addEventListener('readystatechange', function(e) {
            if (xhr.readyState == 4 && xhr.status == 200) {
                updateProgress(i, 100); // <- Add this
                let data = JSON.parse(xhr.responseText);
                console.log(data);
                console.log(data.url);
                datareturn.style.display = "flex";
                urlreturn.value = data.url;
            } else if (xhr.readyState == 4 && xhr.status != 200) {
                alert('error');
            }
        });

        formData.append('upload_preset', 'fnzeexyn');
        formData.append('file', file);
        xhr.send(formData);

        setTimeout( () => {
            progressBar.value = 0;
            document.getElementById("gallery").innerHTML = "";
        }, 10000);
    }
});