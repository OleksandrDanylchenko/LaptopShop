<#macro excelUpload tableNameEn tableNameUk>
    <div class="modal fade" id="${tableNameEn}ExcelUploadModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="${tableNameEn}UploadLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="${tableNameEn}UploadLabel">
                        Імпорт Excel файлу в таблицю ${tableNameUk}
                    </div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/${tableNameEn?lower_case}/importExcel" enctype="multipart/form-data"
                          id="excelUploadForm" class="form-group needs-validation" novalidate>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Excel файл</span>
                            </div>
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" accept=".xlsx"
                                       name="uploadingFile" required>
                                <label class="custom-file-label" data-browse="Огляд">Оберіть файл</label>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>

                    <script>
                        // Script to provide the name of the file appear on select
                        $(".custom-file-input").change(function () {
                            let fileName = $(this).val().split("\\").pop();
                            $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
                        });
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="excelUploadForm" class="btn btn-success float-right">
                        Імпортувати
                    </button>
                </div>
            </div>
        </div>
    </div>
</#macro>