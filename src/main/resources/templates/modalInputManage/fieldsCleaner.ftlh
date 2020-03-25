<#macro fieldsCleaner modalId formId inputIds>
    <script>
        $("#${modalId}").on('hidden.bs.modal', function(){
            <#list inputIds as inputId>
                document.getElementById("${inputId}").value = "";
            </#list>
            var form = document.getElementById("${formId}");
            form.classList.remove('was-validated');
        });
    </script>
</#macro>