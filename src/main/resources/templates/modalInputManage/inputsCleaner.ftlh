<#macro inputsCleaner modalId inputIds>
    <script>
        $("#${modalId}").on('hidden.bs.modal', function () {
            <#list inputIds as inputId>
                $("#${inputId}").val("").change();
            </#list>
        });
    </script>
</#macro>