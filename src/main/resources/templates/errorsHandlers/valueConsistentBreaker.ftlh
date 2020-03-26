<#macro valueBreaker id inputIds>
    <script>
        function validateForm${id}() {
            <#list inputIds as inputId>
                if (!${inputId}.value.match(/^\d*.*[a-zа-яA-ZА-Я]+.*/))
                    ${inputId}.value = "";
            </#list>
        }
    </script>
</#macro>