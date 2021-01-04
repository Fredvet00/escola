import { element, by, ElementFinder } from 'protractor';

export class ProvaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-prova div table .btn-danger'));
  title = element.all(by.css('jhi-prova div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ProvaUpdatePage {
  pageTitle = element(by.id('jhi-prova-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  numquestoesInput = element(by.id('field_numquestoes'));
  enunciadoInput = element(by.id('field_enunciado'));
  textoInput = element(by.id('field_texto'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setNumquestoesInput(numquestoes: string): Promise<void> {
    await this.numquestoesInput.sendKeys(numquestoes);
  }

  async getNumquestoesInput(): Promise<string> {
    return await this.numquestoesInput.getAttribute('value');
  }

  async setEnunciadoInput(enunciado: string): Promise<void> {
    await this.enunciadoInput.sendKeys(enunciado);
  }

  async getEnunciadoInput(): Promise<string> {
    return await this.enunciadoInput.getAttribute('value');
  }

  async setTextoInput(texto: string): Promise<void> {
    await this.textoInput.sendKeys(texto);
  }

  async getTextoInput(): Promise<string> {
    return await this.textoInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProvaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-prova-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-prova'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
