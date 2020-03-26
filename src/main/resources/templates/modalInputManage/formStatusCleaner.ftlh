<#macro fieldsStatusCleaner modalId formId>
    <script>
        $("#${modalId}").on('hidden.bs.modal', function () {
            var form = document.getElementById("${formId}");
            form.classList.remove('was-validated');
        });
    </script>
</#macro>