import hudson.model.FreeStyleProject;
import hudson.plugins.git.GitSCM;
import hudson.plugins.git.BranchSpec;
import hudson.triggers.SCMTrigger;
import javaposse.jobdsl.plugin.*;
import jenkins.model.Jenkins;

jenkins = Jenkins.instance;
jobName = "create-dsl-job";
dslProject = new hudson.model.FreeStyleProject(jenkins, jobName);

gitSCM = Jenkins.instance.getExtensionList(hudson.plugins.git.GitSCM.DescriptorImpl.class)[0];
gitSCM.globalConfigName = "jenkins";
gitSCM.globalConfigEmail = "jenkins@localhost.org"
gitSCM.save();

dslProject.scm = new GitSCM("https://github.com/aduprat/jenkins-demo.git");
dslProject.scm.branches = [new BranchSpec("*/3")];

gitTrigger = new SCMTrigger("* * * * *");
dslProject.addTrigger(gitTrigger);

dslProject.createTransientActions();
dslBuilder = new ExecuteDslScripts(scriptLocation=new ExecuteDslScripts.ScriptLocation(value = "false", targets="build-git-merge", scriptText=""), ignoreExisting=false, removedJobAction=RemovedJobAction.DISABLE);
dslProject.getPublishersList().add(dslBuilder);

jenkins.add(dslProject, jobName);
gitTrigger.start(dslProject, true);
