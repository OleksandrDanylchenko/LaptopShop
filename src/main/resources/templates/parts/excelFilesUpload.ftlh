<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as error>

<@pad.page "${controllerName?cap_first} excel import">
    <@error.errorOutput errorMessage!/>

    <div class="h1">Імпорт Excel файлу в таблицю ${tableName}</div>
    <form method="post" action="importExcel" enctype="multipart/form-data"
          class="form-group needs-validation my-4 w-100" novalidate>
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Excel файл</span>
            </div>
            <div class="custom-file">
                <input type="file" class="custom-file-input" accept=".xls,.xlsx" name="uploadingFile" required>
                <label class="custom-file-label" data-browse="Огляд">Оберіть файл</label>
            </div>
        </div>
        <button type="submit" class="btn btn-success mt-2 float-right">Імпортувати</button>
    </form>

    <script>
        // JS script to provide the name of the file appear on select
        $(".custom-file-input").on("change", function () {
            var fileName = $(this).val().split("\\").pop();
            $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
        });
    </script>
</@pad.page>