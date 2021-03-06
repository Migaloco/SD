package microgram.impl.clt.rest;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import discovery.Discovery;
import microgram.api.Post;
import microgram.api.java.Posts;
import microgram.api.java.Result;
import microgram.api.rest.RestPosts;
import microgram.impl.srv.rest.PostsRestServer;

//TODO Make this class concrete
public class RestPostsClient extends RestClient implements Posts {

	public RestPostsClient(URI serverUri) {
		super(serverUri, RestPosts.PATH);
	}

	public RestPostsClient() {
		super(Discovery.findUrisOf(PostsRestServer.SERVICE, 1)[0], RestPosts.PATH);
	}
	
	public Result<String> createPost(Post post) {
		Response r = target
				.request()
				.post( Entity.entity( post, MediaType.APPLICATION_JSON));
		
		return super.responseContents(r, Status.OK, new GenericType<String>(){});	
	}


	@Override
	public Result<Post> getPost(String postId) {
		// TODO Auto-generated method stub
		Response r = target.path(postId)
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get();
		
		return super.responseContents(r, Status.OK, new GenericType<Post>() {});
	}


	@Override
	public Result<Void> deletePost(String postId) {
		// TODO Auto-generated method stub
		Response r = target.path(postId)
				.request()
				.delete();

		return responseContents(r, Status.OK, new GenericType<Void>() {
		});	}


	@Override
	public Result<Void> like(String postId, String userId, boolean isLiked) {
		// TODO Auto-generated method stub
		Response r = target.path(postId+"/likes/"+userId)
				.request()
				.put(Entity.entity(isLiked, MediaType.APPLICATION_JSON));
		
		return responseContents(r, Status.OK, new GenericType<Void>() {});
		}


	@Override
	public Result<Boolean> isLiked(String postId, String userId) {
		// TODO Auto-generated method stub
		Response r = target.path(postId+"/likes/"+userId)
				.request()
				.get();

		return responseContents(r, Status.OK, new GenericType<Boolean>() {
		});

	}


	@Override
	public Result<List<String>> getPosts(String userId) {
		// TODO Auto-generated method stub
		Response r = target.path("/from/"+userId)
				.request()
				.get();

		return responseContents(r, Status.OK, new GenericType<List<String>>() {
		});
	}


	@Override
	public Result<List<String>> getFeed(String userId) {
		// TODO Auto-generated method stub
		Response r = target.path("/from/"+userId)
				.request()
				.get();
		return responseContents(r, Status.OK, new GenericType<List<String>>() {});
	}
}
