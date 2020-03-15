<#macro emptyBreaker inputNames>
    <script>
         <#list inputNames as inputName>
            const ${inputName} = document.querySelector('[name = "${inputName}"]');
            ${inputName}.onkeyup = function() {
                if (this.value.match(/^[ ]+$/))
                    this.value = "";
            };
         </#list>
    </script>
</#macro>