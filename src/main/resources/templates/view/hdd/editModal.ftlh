<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro hddEdit id hddModel hddMemory>
    <div class="modal fade" id="hddEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="hddEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h4" id="hddEditLabel${id}">Змінити інформацію про HDD диск</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="hddEditForm${id}" action="/hdd/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
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
                                <label class="d-inline ml-2" for="memory">Обсяг пам'яті (GiB):</label>
                                <input type="number" id="editMemoryInput${id}" min="1" max="10000" class="form-control"
                                       name="editMemory" required/>
                                <div class="invalid-feedback">Обсяг пам'яті задано некоректно!</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editModelInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editModelInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="hddEditModal${id}" formId="hddEditForm${id}"/>

                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#hddEditModal${id}').on('show.bs.modal', function () {
                            $("#editModelInput${id}").val("${hddModel}").change();
                            $("#editMemoryInput${id}").val("${hddMemory}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="hddEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>