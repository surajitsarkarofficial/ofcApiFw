package endpoints.submodules.project;

import endpoints.GlowEndpoints;

/**
 * @author german.massello
 *
 */
public class ProjectEndpoints extends GlowEndpoints {
	public static String getProjectsFlags = "/proxy/glow/projectservice/projects/flags?projectState=%s&expense=%s&projectName=%s";
	public static String getProjects = "/proxy/glow/projectservice/projects?projectState=%s&filterNameIncluding=%s";
}
