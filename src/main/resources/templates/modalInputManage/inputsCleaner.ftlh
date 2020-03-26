<#macro inputsCleaner modalId inputIds>
    <script>
        $("#${modalId}").on('hidden.bs.modal', function () {
            <#list inputIds as inputId>
            document.getElementById("${inputId}").value = "";
            </#list>
        });
    </script>
</#macro>