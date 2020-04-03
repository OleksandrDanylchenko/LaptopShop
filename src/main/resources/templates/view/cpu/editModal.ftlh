<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro cpuEdit id cpuModel cpuFrequency>
    <div class="modal fade" id="cpuEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="cpuEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="cpuEditLabel${id}">Змінити інформацію про процесор</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="cpuEditForm${id}" action="/cpu/edit/${id}"
                          class="form-group needs-validation w-100" novalidate
                          onsubmit="return validateForm${id}()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Назва змінюваної моделі:</label>
                                <input type="text" id="editModelInput${id}" class="form-control" name="editModel"
                                       required/>
                                <div class="invalid-feedback">Назву змінюваної моделі задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="frequency">Тактова частота (GhZ):</label>
                                <input type="number" id="editFrequencyInput${id}" step="0.1" min="0.1" max="10"
                                       class="form-control" name="editFrequency" required/>
                                <div class="invalid-feedback">Тактову частоту задано некоректно!</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editModelInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editModelInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="cpuEditModal${id}" formId="cpuEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#cpuEditModal${id}').on('show.bs.modal', function () {
                            $("#editModelInput${id}").val("${cpuModel}").change();
                            $("#editFrequencyInput${id}").val("${cpuFrequency}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="cpuEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>