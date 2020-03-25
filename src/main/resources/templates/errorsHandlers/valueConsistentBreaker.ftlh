<#macro valueBreaker type inputIds>
    <script>
        function validate${type}Form() {
            <#list inputIds as inputId>
                if (!${inputId}.value.match(/^\d*.*[a-zа-яA-ZА-Я]+.*/))
                    ${inputId}.value = "";
            </#list>
        }
    </script>
</#macro>