<#macro selectsCleaner modalId selectIds>
    <script>
        $("#${modalId}").on('hidden.bs.modal', function () {
            <#list selectIds as selectId>
                document.getElementById('${selectId}').selectedIndex = 0;
            </#list>
        });
    </script>
</#macro>