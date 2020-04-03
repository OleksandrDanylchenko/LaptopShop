<#macro datesBreaker id dateStartId dateEndId>
    <script>
        function validateDates${id}() {
            let dateStart = $("#${dateStartId}").val();
            let dateEnd = $("#${dateEndId}").val();
            if (dateStart > dateEnd)
                $("#${dateEndId}").val("").change();
        }
    </script>
</#macro>