<#macro fieldsStatusCleaner modalId formId>
    <script>
        $("#${modalId}").on('hidden.bs.modal', function () {
            $("#${formId}").removeClass('was-validated');
        });
    </script>
</#macro>