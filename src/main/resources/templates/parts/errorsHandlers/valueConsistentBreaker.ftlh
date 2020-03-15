<#macro valueBreaker inputNames>
    <script>
        function validateForm() {
            <#list inputNames as inputName>
                const ${inputName} = document.querySelector('[name = "${inputName}"]');
                if (!${inputName}.value.match(/^\d*.*[a-zа-яA-ZА-Я]+.*/)) {
                    ${inputName}.value = "";
                }
            </#list>
        }
    </script>
</#macro>