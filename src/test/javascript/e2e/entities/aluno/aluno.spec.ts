import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AlunoComponentsPage, AlunoDeleteDialog, AlunoUpdatePage } from './aluno.page-object';

const expect = chai.expect;

describe('Aluno e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let alunoComponentsPage: AlunoComponentsPage;
  let alunoUpdatePage: AlunoUpdatePage;
  let alunoDeleteDialog: AlunoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Alunos', async () => {
    await navBarPage.goToEntity('aluno');
    alunoComponentsPage = new AlunoComponentsPage();
    await browser.wait(ec.visibilityOf(alunoComponentsPage.title), 5000);
    expect(await alunoComponentsPage.getTitle()).to.eq('escolaApp.aluno.home.title');
    await browser.wait(ec.or(ec.visibilityOf(alunoComponentsPage.entities), ec.visibilityOf(alunoComponentsPage.noResult)), 1000);
  });

  it('should load create Aluno page', async () => {
    await alunoComponentsPage.clickOnCreateButton();
    alunoUpdatePage = new AlunoUpdatePage();
    expect(await alunoUpdatePage.getPageTitle()).to.eq('escolaApp.aluno.home.createOrEditLabel');
    await alunoUpdatePage.cancel();
  });

  it('should create and save Alunos', async () => {
    const nbButtonsBeforeCreate = await alunoComponentsPage.countDeleteButtons();

    await alunoComponentsPage.clickOnCreateButton();

    await promise.all([alunoUpdatePage.setNomeInput('nome'), alunoUpdatePage.setIdadeInput('5')]);

    expect(await alunoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await alunoUpdatePage.getIdadeInput()).to.eq('5', 'Expected idade value to be equals to 5');

    await alunoUpdatePage.save();
    expect(await alunoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await alunoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Aluno', async () => {
    const nbButtonsBeforeDelete = await alunoComponentsPage.countDeleteButtons();
    await alunoComponentsPage.clickOnLastDeleteButton();

    alunoDeleteDialog = new AlunoDeleteDialog();
    expect(await alunoDeleteDialog.getDialogTitle()).to.eq('escolaApp.aluno.delete.question');
    await alunoDeleteDialog.clickOnConfirmButton();

    expect(await alunoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
