<#macro emptyBreaker inputIds>
    <script>
        <#list inputIds as inputId>
            document.getElementById("${inputId}").onkeyup = function () {
                if (this.value.match(/^[ ]+$/))
                    this.value = "";
            };
        </#list>
    </script>
</#macro>