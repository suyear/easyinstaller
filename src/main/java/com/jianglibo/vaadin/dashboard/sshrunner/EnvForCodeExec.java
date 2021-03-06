package com.jianglibo.vaadin.dashboard.sshrunner;

import java.io.IOException;
import java.io.StringReader;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.jianglibo.vaadin.dashboard.domain.Box;
import com.jianglibo.vaadin.dashboard.domain.BoxGroup;
import com.jianglibo.vaadin.dashboard.domain.Software;
import com.jianglibo.vaadin.dashboard.domain.TextFile;
import com.jianglibo.vaadin.dashboard.service.AppObjectMappers;
import com.jianglibo.vaadin.dashboard.taskrunner.OneThreadTaskDesc;
import com.jianglibo.vaadin.dashboard.util.SoftwareUtil;
import com.jianglibo.vaadin.dashboard.vo.ConfigContent;

/**
 * This Object will be encoded to user preferred format and upload to target
 * server as a file. can be used in user's code.
 * 
 * @author jianglibo@gmail.com
 *
 */
public class EnvForCodeExec {
	
	private final String remoteFolder;
	private final BoxDescription box;
	private final BoxGroupDescription boxGroup;
	private final SoftwareDescription software;
	
	private EnvForCodeExec(BoxDescription box, BoxGroupDescription boxGroup, SoftwareDescription software, String remoteFolder) {
		this.remoteFolder = remoteFolder;
		this.box = box;
		this.boxGroup = boxGroup;
		this.software = software;
	}

	public String getRemoteFolder() {
		return remoteFolder;
	}

	public BoxDescription getBox() {
		return box;
	}

	public BoxGroupDescription getBoxGroup() {
		return boxGroup;
	}

	public SoftwareDescription getSoftware() {
		return software;
	}

	public static class BoxDescription {
		private long id;
		private String ip;
		private String name;
		private String hostname;
		private String roles;
		private String dnsServer;
		private String ips;
		private String ports;
		
		private String boxRoleConfig;

		public BoxDescription(Box box) {
			this.ip = box.getIp();
			this.name = box.getName();
			this.hostname = box.getHostname();
			this.roles = box.getRoles();
			this.dnsServer = box.getDnsServer();
			this.ips= box.getIps();
			this.ports= box.getPorts();
			this.id = box.getId();
		}
		
		public String getRoles() {
			return roles;
		}


		public void setRoles(String roles) {
			this.roles = roles;
		}


		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getHostname() {
			return hostname;
		}

		public void setHostname(String hostname) {
			this.hostname = hostname;
		}

		public String getDnsServer() {
			return dnsServer;
		}

		public void setDnsServer(String dnsServer) {
			this.dnsServer = dnsServer;
		}

		public String getIps() {
			return ips;
		}

		public void setIps(String ips) {
			this.ips = ips;
		}

		public String getPorts() {
			return ports;
		}

		public void setPorts(String ports) {
			this.ports = ports;
		}

		public String getBoxRoleConfig() {
			return boxRoleConfig;
		}

		public void setBoxRoleConfig(String boxRoleConfig) {
			this.boxRoleConfig = boxRoleConfig;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	}
	
	public static class SoftwareDescription {
		private String name;
		private String sversion;
		private String ostype;
		
		private String runas;
		
		private String timeouts;
		
		private String runner;
		
		private String possibleRoles;
		
		private Set<TextFileDescription> textfiles;
		
		private Set<String> filesToUpload;
		private String configContent;
		private String actions;
		private String preferredFormat;
		
		public SoftwareDescription(Software software) {
			this.name = software.getName();
			this.sversion = software.getSversion();
			this.ostype = software.getOstype();
			this.filesToUpload = software.getFilesToUpload();
			this.configContent = software.getConfigContent();
			this.actions = software.getActions();
			this.preferredFormat = software.getPreferredFormat();
			this.runas = software.getRunas();
			this.runner = software.getRunner();
			this.textfiles = software.getTextfiles().stream().map(TextFileDescription::new).collect(Collectors.toSet());
			this.timeouts = software.getTimeouts();
			this.possibleRoles = software.getPossibleRoles();
		}

		public Set<String> getFilesToUpload() {
			return filesToUpload;
		}

		public void setFilesToUpload(Set<String> filesToUpload) {
			this.filesToUpload = filesToUpload;
		}

		public String getConfigContent() {
			return configContent;
		}

		public void setConfigContent(String configContent) {
			this.configContent = configContent;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSversion() {
			return sversion;
		}

		public void setSversion(String sversion) {
			this.sversion = sversion;
		}

		public String getOstype() {
			return ostype;
		}

		public void setOstype(String ostype) {
			this.ostype = ostype;
		}

		public String getActions() {
			return actions;
		}

		public void setActions(String actions) {
			this.actions = actions;
		}

		public String getPreferredFormat() {
			return preferredFormat;
		}

		public void setPreferredFormat(String preferredFormat) {
			this.preferredFormat = preferredFormat;
		}

		public String getRunas() {
			return runas;
		}

		public void setRunas(String runas) {
			this.runas = runas;
		}

		public String getRunner() {
			return runner;
		}

		public void setRunner(String runner) {
			this.runner = runner;
		}

		public Set<TextFileDescription> getTextfiles() {
			return textfiles;
		}

		public void setTextfiles(Set<TextFileDescription> textfiles) {
			this.textfiles = textfiles;
		}

		public String getTimeouts() {
			return timeouts;
		}

		public void setTimeouts(String timeouts) {
			this.timeouts = timeouts;
		}

		public String getPossibleRoles() {
			return possibleRoles;
		}

		public void setPossibleRoles(String possibleRoles) {
			this.possibleRoles = possibleRoles;
		}
	}
	
	public static class TextFileDescription {
		private String name;
		private String content;
		private String codeLineSeperator;
		
		public TextFileDescription(TextFile tf) {
			this.name = tf.getName();
			try {
				this.setContent(Joiner.on(SoftwareUtil.parseLs(tf.getCodeLineSeperator())).join(CharStreams.readLines(new StringReader(tf.getContent()))));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.codeLineSeperator = tf.getCodeLineSeperator();
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getCodeLineSeperator() {
			return codeLineSeperator;
		}
		public void setCodeLineSeperator(String codeLineSeperator) {
			this.codeLineSeperator = codeLineSeperator;
		}
	}

	public static class BoxGroupDescription {
		private long id;
		private String name;
		
		private String configContent;
		
		private String installResults;

		private List<BoxDescription> boxes;

		public BoxGroupDescription(BoxGroup bg) {
			this.name = bg.getName();
			this.id = bg.getId();
			this.configContent = bg.getConfigContent();
			this.installResults = bg.getInstallResults();

			this.setBoxes(bg.getBoxes().stream().map(b -> {
				BoxDescription bd = new BoxDescription(b);
				if (Strings.isNullOrEmpty(b.getDnsServer())) {
					bd.setDnsServer(bg.getDnsServer());
				}
				return bd;
			}).collect(Collectors.toList()));
			
			// return consist result for test.
			this.getBoxes().sort(new Comparator<BoxDescription>() {
				@Override
				public int compare(BoxDescription bd1, BoxDescription bd2) {
					return bd1.getIp().compareTo(bd2.getIp());
				}
			});
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getConfigContent() {
			return configContent;
		}

		public void setConfigContent(String configContent) {
			this.configContent = configContent;
		}

		public List<BoxDescription> getBoxes() {
			return boxes;
		}

		public void setBoxes(List<BoxDescription> boxes) {
			this.boxes = boxes;
		}

		public String getInstallResults() {
			return installResults;
		}

		public void setInstallResults(String installResults) {
			this.installResults = installResults;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	}
	
	public static class EnvForCodeExecBuilder {
		private final String remoteFolder;
		private final BoxDescription box;
		private final BoxGroupDescription boxGroup;
		private final SoftwareDescription software;
		
		private final AppObjectMappers appObjectMappers;
		
		public EnvForCodeExecBuilder(AppObjectMappers appObjectMappers, OneThreadTaskDesc oneThreadTaskDesc, String remoteFolder) {
			this.remoteFolder = remoteFolder;
			this.appObjectMappers = appObjectMappers;
			this.box = new BoxDescription(oneThreadTaskDesc.getBox());
			this.boxGroup = new BoxGroupDescription(oneThreadTaskDesc.getTaskDesc().getBoxGroup());
			this.software = new SoftwareDescription(oneThreadTaskDesc.getSoftware());
		}
		
		public EnvForCodeExec build() {
			software.setConfigContent(new ConfigContent(software.getConfigContent()).getConverted(appObjectMappers, this.software.getPreferredFormat())); 
			boxGroup.setConfigContent(new ConfigContent(boxGroup.getConfigContent()).getConverted(appObjectMappers, this.software.getPreferredFormat()));
			box.setBoxRoleConfig(new ConfigContent(box.getBoxRoleConfig()).getConverted(appObjectMappers, this.software.getPreferredFormat()));
			return new EnvForCodeExec(box, boxGroup, software, remoteFolder);
		}
		
	}
}
